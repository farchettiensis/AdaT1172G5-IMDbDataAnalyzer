package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.StatisticUtils;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.exceptions.CSVParseException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {

    private final MovieDAO movieDAO;

    public MovieServiceImpl(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public void csvParseToDB() {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            movieDAO.openTransaction();
            for (Movie movie : movies) {
                movieDAO.save(movie);
            }
            movieDAO.closeTransaction();
        } catch (Exception e) {
            if (movieDAO.getEm().getTransaction().isActive()) {
                movieDAO.getEm().getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Movie saveMovie(Movie movie) {
        movieDAO.openTransaction().save(movie).closeTransaction();
        return movie;
    }

    @Override
    public Movie getMovieById(String id) {
        try {
            return movieDAO.getMovieById(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMovie(Movie movie) {
        movieDAO.openTransaction().delete(movie).closeTransaction();
    }

    @Override
    public Movie updateMovie(String id, Movie movie) {
        try {
            movieDAO.openTransaction().update(id,movie).closeTransaction();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movie;
    }

    @Override
    public void closeService() {
        movieDAO.closeDAO();
    }

    @Override
    public List<Movie> fetchAllMovies() {
        try {
            return movieDAO.getAllMoviesFromDB();
        } catch (IOException e) {
            throw new CSVParseException("Erro ao fazer parse: " + e.getMessage());
        }
    }

    @Override
    public List<Movie> fetchAllMoviesFromDB() {
        try {
            return movieDAO.getAllMoviesFromDB();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Genres, Double> getAverageRatingPerGenre(List<Movie> movies) {
        return Arrays.stream(Genres.values())
                .collect(Collectors.toMap(
                        genre -> genre,
                        genre -> movies.stream()
                                .filter(movie -> movie.getGenres().contains(genre))
                                .mapToDouble(movie -> movie.getAverageRating())
                                .average()
                                .orElse(0.0)
                ));
    }

    @Override
    public Map<Genres, Double> getAverageNumVotesPerGenre(List<Movie> movies) {
        return Arrays.stream(Genres.values())
                .collect(Collectors.toMap(
                        genre -> genre,
                        genre -> movies.stream()
                                .filter(movie -> movie.getGenres().contains(genre))
                                .mapToInt(movie -> movie.getNumVotes())
                                .average()
                                .orElse(0.0)
                ));
    }

    @Override
    public double calculateCorrelationBetweenVotesAndRatings(List<Movie> movies) {
        Map<Genres, Double> averageRatings = getAverageRatingPerGenre(movies);
        Map<Genres, Double> averageNumVotes = getAverageNumVotesPerGenre(movies);

        List<Double> avgRatingsList = new ArrayList<>();
        List<Double> avgNumVotesList = new ArrayList<>();

        Arrays.stream(Genres.values())
                .map(genre -> new AbstractMap.SimpleEntry<>(
                        averageRatings.get(genre),
                        averageNumVotes.get(genre)))
                .filter(entry -> entry.getKey() != null && entry.getValue() != null && entry.getValue() > 0)
                .forEach(entry -> {
                    avgRatingsList.add(entry.getKey());
                    avgNumVotesList.add(entry.getValue());
                });

        return StatisticUtils.calculateCorrelation(avgRatingsList, avgNumVotesList);
    }


}
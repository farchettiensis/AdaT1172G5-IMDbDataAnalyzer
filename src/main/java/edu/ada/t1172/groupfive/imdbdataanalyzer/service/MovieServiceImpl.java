package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.StatisticUtils;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.exceptions.CSVParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {

    private final MovieDAO movieDAO;

    public MovieServiceImpl(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public List<Movie> fetchAllMovies() {
        try {
            return movieDAO.getAllMovies();
        } catch (IOException e) {
            throw new CSVParseException("Erro ao fazer parse: " + e.getMessage());
        }
    }

    @Override
    public List<Movie> getMoviesByGenre(List<Movie> movies, Genres genre) {
        return movies.stream()
                .filter(movie -> movie.getGenres().contains(genre))
                .collect(Collectors.toList());
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

        for (Genres genre : Genres.values()) {
            Double avgRating = averageRatings.get(genre);
            Double avgNumVotesValue = averageNumVotes.get(genre);

            if (avgRating != null && avgNumVotesValue != null && avgNumVotesValue > 0) {
                avgRatingsList.add(avgRating);
                avgNumVotesList.add(avgNumVotesValue);
            }
        }

        return StatisticUtils.calculateCorrelation(avgRatingsList, avgNumVotesList);
    }

}
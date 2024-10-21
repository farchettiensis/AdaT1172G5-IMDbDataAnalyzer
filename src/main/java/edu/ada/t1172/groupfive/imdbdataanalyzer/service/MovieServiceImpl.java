package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.exceptions.CSVParseException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {

    private final MovieDAO movieDAO;

    public MovieServiceImpl(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

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
                .filter(movie -> movie.getGenres().contains(genre)).collect(Collectors.toList());
    }

    @Override
    public List<Movie> getMoviesByYear(List<Movie> movies, Integer year) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() == year)
                .toList();
    }

    @Override
    public Movie getTopRatedMovie(List<Movie> movies) {
        return movies.stream()
                .max(Comparator.comparing(Movie::getAverageRating)).orElse(null);
    }

    // TODO: feito com duas abordagens, deixar apenas uma depois dos testes
    @Override
    public Map<Genres, Double> getAverageRatingPerGenre(List<Movie> movies) {
        Map<Genres, Double> totalRatings = new HashMap<>();
        Map<Genres, Integer> counts = new HashMap<>();

        for (Movie movie : movies) {
            double rating = movie.getAverageRating();
            for (Genres genre : movie.getGenres()) {
                totalRatings.merge(genre, rating, Double::sum);
                counts.merge(genre, 1, Integer::sum);
            }
        }

        Map<Genres, Double> averageRatings = new HashMap<>();
        for (Genres genre : totalRatings.keySet()) {
            double total = totalRatings.get(genre);
            int count = counts.get(genre);
            averageRatings.put(genre, total / count);
        }

        return averageRatings;
    }

    public Map<Genres, Double> getAverageRatingPerGenre2(List<Movie> movies) {
        return Arrays.stream(Genres.values())
                .collect(Collectors.toMap(
                        genre -> genre,
                        genre -> movies.stream()
                                .filter(movie -> movie.getGenres().contains(genre))
                                .mapToDouble(Movie::getAverageRating)
                                .average()
                                .orElse(0.0)
                ));
    }

}
package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;

import java.util.List;
import java.util.Map;

public interface MovieService {
    List<Movie> fetchAllMovies();

    List<Movie> getMoviesByGenre(List<Movie> movies, Genres genre);

    Map<Genres, Double> getAverageRatingPerGenre(List<Movie> movies);

    Map<Genres, Double> getAverageNumVotesPerGenre(List<Movie> movies);

    double calculateCorrelationBetweenVotesAndRatings(List<Movie> movies);
}

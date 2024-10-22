package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;

import java.util.List;
import java.util.Map;

public interface MovieService {

    void csvParseToDB();
    Movie saveMovie(Movie movie);
    Movie getMovieById(String id);
    void deleteMovie(Movie movie);
    Movie updateMovie(String id, Movie movie);
    void closeService();
    List<Movie> fetchAllMovies();
    List<Movie> fetchAllMoviesFromDB();

    Map<Genres, Double> getAverageRatingPerGenre(List<Movie> movies);

    Map<Genres, Double> getAverageNumVotesPerGenre(List<Movie> movies);

    double calculateCorrelationBetweenVotesAndRatings(List<Movie> movies);
}

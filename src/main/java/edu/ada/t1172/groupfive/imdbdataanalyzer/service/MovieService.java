package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;

import java.util.List;

public interface MovieService {
    List<Movie> fetchAllMovies();

    List<Movie> getMoviesByGenre(List<Movie> movies, Genres genre);

    List<Movie> getMoviesByYear(List<Movie> movies, Integer year);

    Movie getTopRatedMovie(List<Movie> movies);
}

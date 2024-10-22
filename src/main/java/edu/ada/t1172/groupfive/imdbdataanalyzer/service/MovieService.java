package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> fetchAllMovies();
}

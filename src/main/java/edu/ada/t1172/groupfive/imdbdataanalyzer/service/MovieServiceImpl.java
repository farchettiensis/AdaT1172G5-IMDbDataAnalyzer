package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.exceptions.CSVParseException;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
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


}
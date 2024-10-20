package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.exceptions.CSVParseException;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class MovieService {

    private MovieDAO movieDAO;

    public MovieService(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public List<Movie> buscarTodosOsFilmes() {
        try {
            return movieDAO.buscarTodos();
        } catch (IOException e) {
            throw new CSVParseException("Erro ao fazer parse: "+e.getMessage());
        }
    }

    public Movie getTopRatedMovieByGenre(Genres genre){
            return buscarTodosOsFilmes().stream()
                    .filter(movie -> movie.getGenres().contains(genre))
                    .max(Comparator.comparing(Movie::getAverageRating)).orElse(null);
    }

    public Movie getTopRatedMovieByGenreAfterYear(Genres genre, Integer year){
        return buscarTodosOsFilmes().stream()
                .filter(movie -> movie.getGenres().contains(genre) && movie.getReleaseYear() >= year)
                .max(Comparator.comparing(Movie::getAverageRating)).orElse(null);
    }

}

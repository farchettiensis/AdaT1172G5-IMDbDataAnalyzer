package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.exceptions.CSVParseException;

import java.io.IOException;
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
}

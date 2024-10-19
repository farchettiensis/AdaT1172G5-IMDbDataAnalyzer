package edu.ada.t1172.groupfive.imdbdataanalyzer.dao;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;

import java.util.List;

public class MovieDAO {
    private CSVParser csvParser;

    public MovieDAO(CSVParser csvParser) {
        this.csvParser = csvParser;
    }

    public List<Movie> buscarTodos() {
        return csvParser.buscarTodos();
    }
}

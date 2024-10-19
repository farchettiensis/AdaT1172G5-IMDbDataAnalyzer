package edu.ada.t1172.groupfive.imdbdataanalyzer.app;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;

public class Main {
    public static void main(String[] args) {
        String caminhoCSV = "src/main/resources/data.csv";
        MovieService movieService = new MovieService(new MovieDAO(new CSVParser(caminhoCSV)));

        movieService.buscarTodosOsFilmes().stream().limit(100).forEach(System.out::println);
    }
}
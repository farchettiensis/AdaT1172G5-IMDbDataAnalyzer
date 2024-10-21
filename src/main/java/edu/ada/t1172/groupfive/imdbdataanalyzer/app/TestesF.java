package edu.ada.t1172.groupfive.imdbdataanalyzer.app;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieServiceImpl;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;

import java.util.List;
import java.util.Map;

// TODO: arquivo separado para testes temporários, mudar depois para a Main
public class TestesF {
    public static void main(String[] args) {
        CSVParser csvParser = new CSVParser("src/main/resources/data.csv");
        MovieDAO movieDAO = new MovieDAO(csvParser);
        MovieService movieService = new MovieServiceImpl(movieDAO);

        List<Movie> allMovies = movieService.fetchAllMovies();
        Map<Genres, Double> averageRatings = movieService.getAverageRatingPerGenre(allMovies);

        averageRatings.forEach((genre, avgRating) -> {
            System.out.printf("Genre: %s, Average Rating: %.2f%n", genre, avgRating);
        });
    }
}

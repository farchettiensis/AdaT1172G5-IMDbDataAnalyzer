package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.exceptions.CSVParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    public Map<Genres, Double> getAverageRatingPerGenre(List<Movie> movies) {
        return Arrays.stream(Genres.values())
                .collect(Collectors.toMap(
                        genre -> genre,
                        genre -> movies.stream()
                                .filter(movie -> movie.getGenres().contains(genre))
                                .mapToDouble(movie -> movie.getAverageRating())
                                .average()
                                .orElse(0.0)
                ));
    }
}
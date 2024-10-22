package edu.ada.t1172.groupfive.imdbdataanalyzer.questions;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LotharQuestions {
    private MovieService movieService;

    public LotharQuestions(MovieService movieService) {
        this.movieService = movieService;
    }

    public void getTopFiveMoviesFrom20thCentury(List<Movie> movies){
        movies
                .stream()
                .filter(movie -> movie.getReleaseYear() <= 2000)
                .sorted(Comparator.comparingDouble(Movie::getAverageRating).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

    public void getTopFiveMoviesFrom21stCentury(List<Movie> movies){
        movies
                .stream()
                .filter(movie -> movie.getReleaseYear() > 2000)
                .sorted(Comparator.comparingDouble(Movie::getAverageRating).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

}

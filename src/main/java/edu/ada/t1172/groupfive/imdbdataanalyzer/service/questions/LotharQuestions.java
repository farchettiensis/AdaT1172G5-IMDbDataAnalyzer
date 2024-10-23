package edu.ada.t1172.groupfive.imdbdataanalyzer.service.questions;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;

import java.util.Comparator;
import java.util.List;

public class LotharQuestions {
    private final MovieService movieService;

    public LotharQuestions(MovieService movieService) {
        this.movieService = movieService;
    }

    public void getTopFiveMoviesFrom20thCentury(List<Movie> movies) {
        movies
                .stream()
                .filter(movie -> movie.getReleaseYear() <= 2000)
                .sorted(Comparator.comparingDouble(Movie::getAverageRating).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

    public void getTopFiveMoviesFrom21stCentury(List<Movie> movies) {
        movies
                .stream()
                .filter(movie -> movie.getReleaseYear() > 2000)
                .sorted(Comparator.comparingDouble(Movie::getAverageRating).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

    public void getTopTenRatedMoviesFromLessNumVotes(List<Movie> movies) {
        movies
                .stream()
                .sorted(Comparator.comparingInt(Movie::getNumVotes))
                .limit(20)
                .sorted(Comparator.comparingDouble(Movie::getAverageRating).reversed())
                .limit(10)
                .forEach(System.out::println);
    }

    public void getLeastTenRatedMoviesFromLessNumVotes(List<Movie> movies) {
        movies
                .stream()
                .sorted(Comparator.comparingInt(Movie::getNumVotes))
                .limit(20)
                .sorted(Comparator.comparingDouble(Movie::getAverageRating))
                .limit(10)
                .forEach(System.out::println);
    }

}

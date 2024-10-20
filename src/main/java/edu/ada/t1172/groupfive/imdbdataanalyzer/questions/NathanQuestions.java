package edu.ada.t1172.groupfive.imdbdataanalyzer.questions;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.Calculator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NathanQuestions {

    private MovieService movieService;

    public NathanQuestions(MovieService movieService) {
        this.movieService = movieService;
    }

    public List<Movie> getUnderRatedMovies(List<Movie> movieList, int quantity) {
        final double TOP10_PERCENT_MOST_RATED = Calculator.percentilCalc(movieList.stream()
                .map(Movie::getAverageRating).collect(Collectors.toList()),0.90);
        final double TOP10_PERCENT_LESS_VOTED = Calculator.percentilCalc(movieList.stream()
                .map(Movie::getNumVotes).collect(Collectors.toList()),0.10);
        return movieList.stream().filter(movie -> movie.getNumVotes() <= TOP10_PERCENT_LESS_VOTED)
                .filter(movie -> movie.getAverageRating() >=  TOP10_PERCENT_MOST_RATED)
                .sorted(Comparator.comparing(Movie::getAverageRating).reversed())
                .limit(quantity).collect(Collectors.toList());
    }

    public List<Movie> getOverRatedMovies(List<Movie> movieList, int quantity) {
        final double TOP10_PERCENT_LESS_RATED = Calculator.percentilCalc(movieList.stream()
                .map(Movie::getAverageRating).collect(Collectors.toList()),0.10);
        final double TOP10_PERCENT_MOST_VOTED = Calculator.percentilCalc(movieList.stream()
                .map(Movie::getNumVotes).collect(Collectors.toList()),0.90);
        return movieList.stream().filter(movie -> movie.getNumVotes() >= TOP10_PERCENT_MOST_VOTED)
                .filter(movie -> movie.getAverageRating() <=  TOP10_PERCENT_LESS_RATED)
                .sorted(Comparator.comparing(Movie::getAverageRating).reversed())
                .limit(quantity).collect(Collectors.toList());
    }

}

package edu.ada.t1172.groupfive.imdbdataanalyzer.app;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.questions.*;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieServiceImpl;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String caminhoCSV = "src/main/resources/data.csv";

        MovieServiceImpl movieService = new MovieServiceImpl(new MovieDAO(new CSVParser(caminhoCSV)));
        List<Movie> movies = movieService.fetchAllMovies();

        VictorFerreiraQuestions victorFerreiraQuestions = new VictorFerreiraQuestions(movies);
        victorFerreiraQuestions.question10();

        VictorFariaQuestions questions = new VictorFariaQuestions(movieService);
        System.out.println(questions.getVoteRatingCorrelationForMoviesAfter(2023));
        System.out.println(questions.getGenresWithGreatestRatingVariationByDecade(1921));

        NathanQuestions nathanQuestions = new NathanQuestions(movieService);
        System.out.println("\nUnderRatedMovies:");
        nathanQuestions.getUnderRatedMovies(movies, 5).forEach(System.out::println);
        System.out.println("\nOverRatedMovies:");
        nathanQuestions.getOverRatedMovies(movies, 5).forEach(System.out::println);
        System.out.println("\nThe average rating over the years: " + nathanQuestions.getScoreTendencyOverYears(movies));

        HorrorGenreAnalysis perguntasFernando = new HorrorGenreAnalysis(movieService);
        perguntasFernando.performAnalysis();

        LotharQuestions lotharQuestions = new LotharQuestions(movieService);
        System.out.println("\nTop 5 rated Movies from 20th Century:");
        lotharQuestions.getTopFiveMoviesFrom20thCentury(movies);
        System.out.println("\nTop 5 rated Movies from 21st Century:");
        lotharQuestions.getTopFiveMoviesFrom21stCentury(movies);
        System.out.println("\nTop 10 rated Movies between less voted:");
        lotharQuestions.getTopTenRatedMoviesFromLessNumVotes(movies);
        System.out.println("\nLeast 10 rated Movies between less voted:");
        lotharQuestions.getLeastTenRatedMoviesFromLessNumVotes(movies);
    }
}
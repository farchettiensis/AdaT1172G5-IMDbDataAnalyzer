package edu.ada.t1172.groupfive.imdbdataanalyzer.app;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.questions.NathanQuestions;
import edu.ada.t1172.groupfive.imdbdataanalyzer.questions.VictorFariaQuestions;
import edu.ada.t1172.groupfive.imdbdataanalyzer.questions.VictorFerreiraQuestions;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieServiceImpl;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String caminhoCSV = "src/main/resources/data.csv";

        MovieServiceImpl movieService = new MovieServiceImpl(new MovieDAO(new CSVParser(caminhoCSV)));
        List<Movie> movies = movieService.fetchAllMovies();

        VictorFariaQuestions questions = new VictorFariaQuestions(movieService);
        System.out.println(questions.getVoteRatingCorrelationForMoviesAfter(2023));
        System.out.println(questions.getGenresWithGreatestRatingVariationByDecade(1921));

        System.out.println(
                movieService.getTopRatedMovie(movieService.getMoviesByYear(movieService.fetchAllMovies(), 2020))
        );

        System.out.println(
            movieService.getTopRatedMovie(
                    movieService.getMoviesByGenre(
                            movieService.fetchAllMovies(), Genres.DRAMA))
        );


        System.out.println(movieService.getMoviesByYear(
                movieService.getMoviesByGenre(movieService.fetchAllMovies(), Genres.DRAMA),
                1996
        ));

        VictorFerreiraQuestions victorFerreiraQuestions = new VictorFerreiraQuestions(movieService);

        System.out.println(
                victorFerreiraQuestions.questionOne()
        );

        NathanQuestions nathanQuestions = new NathanQuestions(movieService);
        System.out.println("\nUnderRatedMovies:");
        nathanQuestions.getUnderRatedMovies(movies, 5).forEach(System.out::println);
        System.out.println("\nOverRatedMovies:");
        nathanQuestions.getOverRatedMovies(movies, 5).forEach(System.out::println);

        System.out.println("\nThe average rating over the years: " + nathanQuestions.getScoreTendencyOverYears(movies));
    }
}
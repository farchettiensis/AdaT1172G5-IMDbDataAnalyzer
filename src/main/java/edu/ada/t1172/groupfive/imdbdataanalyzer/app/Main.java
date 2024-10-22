package edu.ada.t1172.groupfive.imdbdataanalyzer.app;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.questions.RatingTendencyAnalysis;
import edu.ada.t1172.groupfive.imdbdataanalyzer.questions.VictorFerreiraQuestions;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieServiceImpl;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String caminhoCSV = "src/main/resources/data.csv";
        MovieServiceImpl movieService = new MovieServiceImpl(new MovieDAO(new CSVParser(caminhoCSV)));
        List<Movie> movies = movieService.fetchAllMovies();
//
//        System.out.println(
//                movieService.getTopRatedMovie(movieService.getMoviesByYear(movieService.fetchAllMovies(), 2020))
//        );
//
//        System.out.println(
//            movieService.getTopRatedMovie(
//                    movieService.getMoviesByGenre(
//                            movieService.fetchAllMovies(), Genres.DRAMA))
//        );
//
//
//        System.out.println(movieService.getMoviesByYear(
//                movieService.getMoviesByGenre(movieService.fetchAllMovies(), Genres.DRAMA),
//                1996
//        ));
//    }

        VictorFerreiraQuestions victorFerreiraQuestions = new VictorFerreiraQuestions(movieService);

        System.out.println(
                victorFerreiraQuestions.questionOne()
        );

        RatingTendencyAnalysis ratingTendencyAnalysis = new RatingTendencyAnalysis(movieService);
        ratingTendencyAnalysis.performAnalysis();
    }
}
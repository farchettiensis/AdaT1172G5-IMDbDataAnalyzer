package edu.ada.t1172.groupfive.imdbdataanalyzer.app;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.questions.VictorFerreiraQuestions;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieServiceImpl;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;

public class Main {
    public static void main(String[] args) {
        String caminhoCSV = "src/main/resources/data.csv";
        MovieServiceImpl movieService = new MovieServiceImpl(new MovieDAO(new CSVParser(caminhoCSV)));
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

    }
}
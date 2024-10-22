package edu.ada.t1172.groupfive.imdbdataanalyzer.app;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.questions.*;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.H2ServerConfig;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieServiceImpl;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws SQLException {

        H2ServerConfig.start(); // inicializacao do BD
        String caminhoCSV = "src/main/resources/data.csv";
        MovieServiceImpl movieService = new MovieServiceImpl(new MovieDAO(new CSVParser(caminhoCSV)));

        movieService.csvParseToDB(); //conversao do CSV para o BD

        List<Movie> movies = movieService.fetchAllMovies();

        VictorFerreiraQuestions victorFerreiraQuestions = new VictorFerreiraQuestions(movies);
        victorFerreiraQuestions.question10();

        VictorFariaQuestions questions = new VictorFariaQuestions(movieService);
        System.out.println(questions.getVoteRatingCorrelationForMoviesAfter(2023));
        System.out.println(questions.getGenresWithGreatestRatingVariationByDecade(1921));
        System.out.println();

        RatingTendencyAnalysis nathanQuestions = new RatingTendencyAnalysis(movieService);
        nathanQuestions.performAnalysis();
        System.out.println();

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


        /*
        * Teste de operacoes CRUD do banco de dados (BD)
         */
        List<Movie> filmes = movieService.fetchAllMovies();
        filmes.stream().limit(20).forEach(System.out::println);
        System.out.println();
        Movie insertTeste = new Movie("tt1843303", "VelociPastor",
                Set.of(Genres.HORROR, Genres.COMEDY), 10d, 1000000, 2018);
        movieService.saveMovie(insertTeste);
        Movie searchTeste = movieService.getMovieById("tt0111161");
        System.out.println(searchTeste);
        System.out.println();
        Movie updateTeste = new Movie(searchTeste.getId(), "Mudando aqui pra teste",
                searchTeste.getGenres(), 0d, 0, 2020);
        movieService.updateMovie("tt0111161", updateTeste);
        System.out.println(movieService.getMovieById("tt0111161"));

        movieService.deleteMovie(movieService.getMovieById("tt0068646"));


    }
}
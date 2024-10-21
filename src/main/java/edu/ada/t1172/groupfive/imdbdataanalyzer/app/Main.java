package edu.ada.t1172.groupfive.imdbdataanalyzer.app;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.questions.NathanQuestions;
import edu.ada.t1172.groupfive.imdbdataanalyzer.questions.VictorFerreiraQuestions;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieServiceImpl;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.h2.tools.Server;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        try {
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8080").start();
            System.out.println("H2 started at http://localhost:8080");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String caminhoCSV = "src/main/resources/data.csv";
        MovieServiceImpl movieService = new MovieServiceImpl(new MovieDAO(new CSVParser(caminhoCSV)));
        List<Movie> movies = movieService.fetchAllMovies();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDbData");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            for (Movie movie : movies) {
                em.persist(movie);
            }
            em.getTransaction().commit();
            System.out.println("Sucess");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();

        } finally {
            em.close();
            emf.close();
        }
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

        NathanQuestions nathanQuestions = new NathanQuestions(movieService);
        System.out.println("\nUnderRatedMovies:");
        nathanQuestions.getUnderRatedMovies(movies, 5).forEach(System.out::println);
        System.out.println("\nOverRatedMovies:");
        nathanQuestions.getOverRatedMovies(movies, 5).forEach(System.out::println);

        System.out.println("\nThe average rating over the years: " + nathanQuestions.getScoreTendencyOverYears(movies));
    }
}
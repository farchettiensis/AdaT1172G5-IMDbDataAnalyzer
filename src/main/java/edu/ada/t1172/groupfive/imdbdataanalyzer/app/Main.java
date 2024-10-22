package edu.ada.t1172.groupfive.imdbdataanalyzer.app;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.MovieDAO;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieServiceImpl;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.h2.tools.Server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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

        MovieDAO movieDAO = new MovieDAO(new CSVParser(caminhoCSV));

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

        try {
            List<Movie> filmes = movieDAO.getAllMoviesFromDB();
            filmes.stream().limit(20).forEach(System.out::println);
            System.out.println();
            Movie insertTeste = new Movie("tt1843303","VelociPastor",
                    Set.of(Genres.HORROR,Genres.COMEDY),10d,1000000,2018);
            movieService.saveMovie(insertTeste);
            Movie searchTeste = movieService.getMovieById("tt0111161");
            System.out.println(searchTeste);
            System.out.println();
            Movie updateTeste = new Movie(searchTeste.getId(),"Mudando aqui pra teste",
                    searchTeste.getGenres(),0d,0,2020);
            movieService.updateMovie("tt0111161",updateTeste);
            System.out.println(movieService.getMovieById("tt0111161"));

            movieService.deleteMovie(movieService.getMovieById("tt0068646"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
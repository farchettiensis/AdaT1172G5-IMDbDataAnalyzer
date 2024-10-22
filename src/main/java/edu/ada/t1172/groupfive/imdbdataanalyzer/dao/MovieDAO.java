package edu.ada.t1172.groupfive.imdbdataanalyzer.dao;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.io.IOException;
import java.lang.classfile.TypeAnnotation;
import java.util.List;

/*
* implementar CREATE, RETRIEVE, UPDATE, DELETE
 */

public class MovieDAO {
    private final CSVParser csvParser;

    private EntityManagerFactory emf;
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public MovieDAO(CSVParser csvParser) {
        this.csvParser = csvParser;
        try {
            emf = Persistence.createEntityManagerFactory("IMDbData");
        } catch (Exception e) {
            System.out.println("Erro: "+e.getMessage());
        }
        this.em = emf.createEntityManager();

    }

    public List<Movie> getAllMovies() throws IOException {
        return csvParser.getAllMovies();
    }

    public List<Movie> getAllMoviesFromDB() throws IOException {
        String jpql = "select m from Movie m";
        TypedQuery<Movie> query = em.createQuery(jpql, Movie.class);
        return query.getResultList();
    }


    public MovieDAO openTransaction() {
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        em.getTransaction().begin();
        return this;
    }
    public MovieDAO closeTransaction() {
        em.getTransaction().commit();
        return this;
    }

    public MovieDAO save(Movie movie) {
        em.persist(movie);
        return this;
    }
    public MovieDAO update(String id, Movie updatedMovie) throws IOException {
        Movie movieToUpdate = em.find(Movie.class, id);
        updateData(movieToUpdate, updatedMovie);
        em.merge(updatedMovie);
        return this;
    }

    public Movie getMovieById(String id) throws IOException {
        Movie movie = em.find(Movie.class, id);
        if (movie == null) {
            throw new IOException("Movie not found");
        }
        return movie;
    }
    public MovieDAO delete(Movie movie) {
        em.remove(movie);
        return this;
    }

    public void closeDAO() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    private void updateData(Movie movieToUpdate,Movie updatedMovie) {
        movieToUpdate.setTitle(updatedMovie.getTitle());
        movieToUpdate.setAverageRating(updatedMovie.getAverageRating());
        movieToUpdate.setNumVotes(updatedMovie.getNumVotes());
        movieToUpdate.setReleaseYear(updatedMovie.getReleaseYear());
        movieToUpdate.setGenres(updatedMovie.getGenres());
        movieToUpdate.setGenresString(updatedMovie.getGenresString());
    }
}

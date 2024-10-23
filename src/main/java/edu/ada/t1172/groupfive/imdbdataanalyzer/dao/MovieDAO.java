package edu.ada.t1172.groupfive.imdbdataanalyzer.dao;

import edu.ada.t1172.groupfive.imdbdataanalyzer.dao.exceptions.DAOException;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.exceptions.MovieNotFoundException;
import edu.ada.t1172.groupfive.imdbdataanalyzer.utils.CSVParser;
import edu.ada.t1172.groupfive.imdbdataanalyzer.utils.exceptions.CSVParseException;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.List;

public class MovieDAO {
    private final CSVParser csvParser;

    private EntityManagerFactory emf;
    private EntityManager em;

    public MovieDAO(CSVParser csvParser) {
        this.csvParser = csvParser;
        try {
            emf = Persistence.createEntityManagerFactory("IMDbData");
        } catch (Exception e) {
            System.out.println("Erro ao criar EntityManagerFactory: " + e.getMessage());
        }
        this.em = emf.createEntityManager();

    }

    public EntityManager getEm() {
        return em;
    }

    public List<Movie> getAllMovies() {
        try {
            return csvParser.getAllMovies();
        } catch (IOException e) {
            throw new CSVParseException("Erro ao ler dados do CSV");
        }
    }

    public List<Movie> getAllMoviesFromDB() {
        try {
            String jpql = "select m from Movie m";
            TypedQuery<Movie> query = em.createQuery(jpql, Movie.class);
            return query.getResultList();
        } catch (NoResultException e) {
            throw new MovieNotFoundException("Filmes não encontrados");
        } catch (PersistenceException e) {
            throw new DAOException("Erro ao obter todos os filmes");
        }
    }


    public MovieDAO openTransaction() throws IllegalStateException {
        try {
            if (em == null || !em.isOpen()) {
                em = emf.createEntityManager();
            }
            em.getTransaction().begin();
            return this;
        } catch (PersistenceException e) {
            throw new DAOException("Erro ao abrir transacao");
        }
    }

    public MovieDAO closeTransaction() throws IllegalStateException {
        try {
            em.getTransaction().commit();
            return this;
        } catch (PersistenceException e) {
            throw new DAOException("Erro ao fechar transacao");
        }
    }

    public MovieDAO save(Movie movie) {
        try {
            em.persist(movie);
            return this;
        } catch (PersistenceException e) {
            throw new DAOException("Erro ao salvar filme");
        }
    }

    public MovieDAO update(String id, Movie updatedMovie) {
        try {
            Movie movieToUpdate = em.find(Movie.class, id);
            updateData(movieToUpdate, updatedMovie);
            em.merge(updatedMovie);
            return this;
        } catch (PersistenceException e) {
            throw new DAOException("Erro ao atualizar filme");
        }
    }

    public Movie getMovieById(String id) {
        try {
            Movie movie = em.find(Movie.class, id);
            if (movie == null) {
                throw new MovieNotFoundException("Filme nao encontrado");
            }
            return movie;
        } catch (PersistenceException e) {
            throw new DAOException("Erro ao obter o filme");
        }
    }

    public MovieDAO delete(Movie movie) {
        try {
            em.remove(movie);
            return this;
        } catch (PersistenceException e) {
            throw new DAOException("Erro ao deletar filme");
        }
    }

    public void closeDAO() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    private void updateData(Movie movieToUpdate, Movie updatedMovie) {
        movieToUpdate.setTitle(updatedMovie.getTitle());
        movieToUpdate.setAverageRating(updatedMovie.getAverageRating());
        movieToUpdate.setNumVotes(updatedMovie.getNumVotes());
        movieToUpdate.setReleaseYear(updatedMovie.getReleaseYear());
        movieToUpdate.setGenres(updatedMovie.getGenres());
        movieToUpdate.setGenresString(updatedMovie.getGenresString());
    }
}

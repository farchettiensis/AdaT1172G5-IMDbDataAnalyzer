package edu.ada.t1172.groupfive.imdbdataanalyzer.dao;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.CSVParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MovieDAOTest {

    @Test
    void testGetAllMovies() throws IOException {
        String testCsvPath = "src/test/resources/test_movies.csv";
        CSVParser csvParser = new CSVParser(testCsvPath);
        MovieDAO movieDAO = new MovieDAO(csvParser);

        List<Movie> movies = movieDAO.getAllMovies();

        assertNotNull(movies);
        assertEquals(3, movies.size());
    }
}

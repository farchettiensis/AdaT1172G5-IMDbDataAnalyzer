package edu.ada.t1172.groupfive.imdbdataanalyzer.util;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CSVParserTest {
    @Test
    void testGetAllMovies() throws IOException {
        String testCsvPath = "src/test/resources/test_movies.csv";
        CSVParser csvParser = new CSVParser(testCsvPath);

        List<Movie> movies = csvParser.getAllMovies();

        assertNotNull(movies);
        assertEquals(3, movies.size());

        Movie firstMovie = movies.get(0);
        assertEquals("tt0111161", firstMovie.getId());
        assertEquals("The Shawshank Redemption", firstMovie.getTitle());
        assertEquals(Set.of(Genres.DRAMA), firstMovie.getGenres());
        assertEquals(9.3, firstMovie.getAverageRating());
        assertEquals(2952034, firstMovie.getNumVotes());
        assertEquals(1994, firstMovie.getReleaseYear());
    }
}

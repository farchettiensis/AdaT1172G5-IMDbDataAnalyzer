package edu.ada.t1172.groupfive.imdbdataanalyzer.service;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieServiceImplTest {
    @Test
    void testGetAverageRatingPerGenre() {
        Movie movie1 = new Movie("id1", "Movie 1", Set.of(Genres.ACTION, Genres.DRAMA), 8.0, 1000, 2000);
        Movie movie2 = new Movie("id2", "Movie 2", Set.of(Genres.ACTION), 6.0, 800, 2005);
        Movie movie3 = new Movie("id3", "Movie 3", Set.of(Genres.DRAMA), 9.0, 1500, 2010);
        List<Movie> movies = List.of(movie1, movie2, movie3);

        MovieService movieService = new MovieServiceImpl(null);

        Map<Genres, Double> averageRatings = movieService.getAverageRatingPerGenre(movies);

        int totalGenres = Genres.values().length;

        assertEquals(2, averageRatings.size());
        assertEquals(7.0, averageRatings.get(Genres.ACTION));
        assertEquals(8.5, averageRatings.get(Genres.DRAMA));
    }
}

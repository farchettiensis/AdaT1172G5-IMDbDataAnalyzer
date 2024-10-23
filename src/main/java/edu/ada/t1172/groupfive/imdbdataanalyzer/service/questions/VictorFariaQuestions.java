package edu.ada.t1172.groupfive.imdbdataanalyzer.service.questions;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VictorFariaQuestions {

    private final MovieService service;

    public VictorFariaQuestions(MovieService service) {
        this.service = service;
    }

    public Double getVoteRatingCorrelationForMoviesAfter(int year) {
        if (year < 1920 || year > 2024)
            return null;

        List<Movie> filteredMovies = service.fetchAllMovies()
                .stream().filter(m -> m.getReleaseYear() >= year)
                .toList();
        int n = filteredMovies.size();
        if (n == 0) return null;

        double sumVotes = filteredMovies.stream()
                .mapToDouble(Movie::getNumVotes)
                .sum();

        double sumRatings = filteredMovies.stream()
                .mapToDouble(Movie::getAverageRating)
                .sum();

        double sumVotesSquared = filteredMovies.stream()
                .mapToDouble(m -> Math.pow(m.getNumVotes(), 2))
                .sum();

        double sumRatingsSquared = filteredMovies.stream()
                .mapToDouble(m -> Math.pow(m.getAverageRating(), 2))
                .sum();

        double sumProduct = filteredMovies.stream()
                .mapToDouble(m -> m.getNumVotes() * m.getAverageRating())
                .sum();

        double numerator = sumProduct - (sumVotes * sumRatings / n);
        double denominator = Math.sqrt((sumVotesSquared - Math.pow(sumVotes, 2) / n) *
                (sumRatingsSquared - Math.pow(sumRatings, 2) / n));

        return denominator == 0 ? null : numerator / denominator;
    }

    public Genres getGenresWithGreatestRatingVariationByDecade(int decade) {
        if (decade < 1920 || decade > 2024)
            return null;

        List<Genres> listOfGenres = Arrays.stream(Genres.values()).toList();
        List<Movie> filteredMovies = service.fetchAllMovies().stream()
                .filter(m -> m.getReleaseYear() >= decade & m.getReleaseYear() <= decade + 9)
                .toList();

        Map<Genres, Double> mapGenresToRating = new HashMap<>();

        for (Genres genre : listOfGenres) {
            mapGenresToRating.put(genre, getRatingVariation(groupByGenre(filteredMovies, genre)));
        }

        List<Double> rating = mapGenresToRating.values().stream().toList();
        for (int i = 0; i < rating.size(); i++) {
            if (rating.get(i).equals(mapGenresToRating.get(listOfGenres.get(i)))) {
                return listOfGenres.get(i);
            }
        }
        return null;
    }

    private List<Movie> groupByGenre(List<Movie> movies, Genres genre) {
        return movies.stream().filter(m -> m.getGenres().contains(genre)).toList();
    }

    private Double getRatingVariation(List<Movie> movies) {
        double min = movies.stream()
                .map(Movie::getAverageRating)
                .reduce(Double::min).orElse(Double.NaN);

        double max = movies.stream()
                .map(Movie::getAverageRating)
                .reduce(Double::max).orElse(Double.NaN);
        return max - min;
    }

}

package edu.ada.t1172.groupfive.imdbdataanalyzer.questions;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import java.util.*;
import java.util.stream.Collectors;

public class VictorFerreiraQuestions {
    List<Movie> movieList;

    public VictorFerreiraQuestions(List<Movie> movieList) {
        this.movieList = movieList;
    }

    private List<Movie> getMovieWithHighestRatingAndLowestVotes() {
        return movieList.stream()
                .sorted(Comparator
                        .comparing(Movie::getNumVotes)
                        .thenComparing(Movie::getAverageRating))
                .limit(5).toList();
    }

    private List<Movie> getMovieWithLowestRatingAndHighestVotes() {
        return movieList.stream()
                .sorted(Comparator
                        .comparing(Movie::getAverageRating).reversed()
                        .thenComparing(Movie::getNumVotes).reversed()).limit(5).toList();
    }

    private Genres getMostOccurentGenres(List<Movie> movies) {
        Map<Genres, Long> genreFrequency = movies.stream()
                .flatMap(movie -> movie.getGenres().stream())
                .collect(Collectors.groupingBy(genre -> genre, Collectors.counting()));
        return genreFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).get();

    }

    public void question10() {
        System.out.println("Quais os filmes com as maiores discrepâncias entre o número de votos e a média de avaliação, e qual o gênero predominante desses filmes?");
        System.out.println("\nEste é o Top 5 de filmes com a maior nota e menor número de votos:");
        getMovieWithHighestRatingAndLowestVotes().forEach(movie -> System.out.println(movie.toString()));
        System.out.println("O gênero predominante Entre eles é: " + getMostOccurentGenres(getMovieWithHighestRatingAndLowestVotes()));

        System.out.println("\n\nEste é o Top 5 de filmes com a menor nota e maior numero de votos:");
        getMovieWithLowestRatingAndHighestVotes().forEach(movie -> System.out.println(movie.toString()));
        System.out.println("O gênero predominante Entre eles é: " + getMostOccurentGenres(getMovieWithLowestRatingAndHighestVotes()));


    }

}

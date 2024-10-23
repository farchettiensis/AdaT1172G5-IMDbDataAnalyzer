package edu.ada.t1172.groupfive.imdbdataanalyzer.service.questions;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;
import edu.ada.t1172.groupfive.imdbdataanalyzer.utils.StatisticUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Análise das seguintes questões:
 *
 * <ol>
 *   <li>Qual a tendência da avaliação ao longo do tempo?</li>
 *   <li>Quais os 5 filmes mais "subestimados" e os 5 mais "superestimados"?</li>
 * </ol>
 *
 * @author Nathan L.
 */
public class RatingTendencyAnalysis {
    private final MovieService movieService;


    public RatingTendencyAnalysis(MovieService movieService) {
        this.movieService = movieService;
    }

    public void performAnalysis() {
        List<Movie> movies = movieService.fetchAllMovies();
        System.out.println("Qual a tendência da avaliação ao longo do tempo?");
        System.out.println("A tendência de avaliação: " + getRatingTendencyOverYears(movies));

        System.out.println();

        System.out.println("Quais os 5 filmes mais \"subestimados\" e os 5 mais \"superestimados\"?");
        System.out.println("Filmes mais \"subestimados\": ");
        List<Movie> underRatedMovies = getUnderRatedMovies(movies, 5);
        underRatedMovies.forEach(System.out::println);

        System.out.println("\nFilmes mais \"superestimados\": ");
        List<Movie> overRatedMovies = getOverRatedMovies(movies, 5);
        overRatedMovies.forEach(System.out::println);
    }

    public List<Movie> getUnderRatedMovies(List<Movie> movieList, int quantity) {

        final double TOP10_PERCENT_MOST_RATED = StatisticUtils.percentilCalc(movieList.stream()
                .map(Movie::getAverageRating).collect(Collectors.toList()), 0.90);

        final double TOP10_PERCENT_LESS_VOTED = StatisticUtils.percentilCalc(movieList.stream()
                .map(Movie::getNumVotes).collect(Collectors.toList()), 0.10);

        return movieList.stream().filter(movie -> movie.getNumVotes() <= TOP10_PERCENT_LESS_VOTED)
                .filter(movie -> movie.getAverageRating() >= TOP10_PERCENT_MOST_RATED)
                .sorted(Comparator.comparing(Movie::getAverageRating).reversed())
                .limit(quantity).collect(Collectors.toList());
    }

    public List<Movie> getOverRatedMovies(List<Movie> movieList, int quantity) {

        final double TOP10_PERCENT_LESS_RATED = StatisticUtils.percentilCalc(movieList.stream()
                .map(Movie::getAverageRating).collect(Collectors.toList()), 0.10);

        final double TOP10_PERCENT_MOST_VOTED = StatisticUtils.percentilCalc(movieList.stream()
                .map(Movie::getNumVotes).collect(Collectors.toList()), 0.90);

        return movieList.stream().filter(movie -> movie.getNumVotes() >= TOP10_PERCENT_MOST_VOTED)
                .filter(movie -> movie.getAverageRating() <= TOP10_PERCENT_LESS_RATED)
                .sorted(Comparator.comparing(Movie::getAverageRating).reversed())
                .limit(quantity).collect(Collectors.toList());
    }

    public String getRatingTendencyOverYears(List<Movie> movieList) {
        movieList.sort(Comparator.comparing(Movie::getReleaseYear).thenComparing(Movie::getTitle));

        List<Double> xList = movieList.stream().map(Movie::getReleaseYear).map(Integer::doubleValue).collect(Collectors.toList());
        List<Double> yList = movieList.stream().map(Movie::getAverageRating).collect(Collectors.toList());

        double angularCoef = StatisticUtils.linearRegression(xList, yList);

        if (angularCoef > 0) {
            return "aumentou";
        } else if (angularCoef < 0) {
            return "diminuiu";
        } else {
            return "se manteve constante";
        }
    }
}

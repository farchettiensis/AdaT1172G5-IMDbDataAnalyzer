package edu.ada.t1172.groupfive.imdbdataanalyzer.questions;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;
import edu.ada.t1172.groupfive.imdbdataanalyzer.service.MovieService;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.FileUtils;
import edu.ada.t1172.groupfive.imdbdataanalyzer.util.StatisticUtils;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Análise das seguintes questões:
 *
 * <ol>
 *   <li>Existe uma Tendência Temporal nas Avaliações de Filmes de Terror?</li>
 *   <li>Engajamento do Público: Os Filmes de Terror Recebem Menos Votos do que os Gêneros com Melhores Avaliações?</li>
 *   <li>Correlação Entre Popularidade do Gênero e Avaliação Média.</li>
 * </ol>
 *
 * @author Fernando B. A.
 */
public class HorrorGenreAnalysis {

    private final MovieService movieService;

    public HorrorGenreAnalysis(MovieService movieService) {
        this.movieService = movieService;
    }

    public void performAnalysis() {
        List<Movie> allMovies = movieService.fetchAllMovies();

        Map<Genres, Double> averageRatings = movieService.getAverageRatingPerGenre(allMovies);
        Map<Genres, Double> averageNumVotes = movieService.getAverageNumVotesPerGenre(allMovies);

        displayAverageRatingsAndVotes(averageRatings, averageNumVotes); // Informativo
        analyzeHorrorRatingsOverTime(allMovies); // Q1
        compareHorrorGenre(averageRatings, averageNumVotes); // Q2
        calculateAndInterpretCorrelation(allMovies); // Q3
    }

    private void displayAverageRatingsAndVotes(Map<Genres, Double> averageRatings, Map<Genres, Double> averageNumVotes) {
        System.out.println("Avaliação média e votos por gênero:");
        for (Genres genre : Genres.values()) {
            Double avgRating = averageRatings.get(genre);
            Double avgVotes = averageNumVotes.get(genre);
            if (avgRating != null && avgVotes != null && avgVotes > 0) {
                System.out.printf("Gênero: %s, Média de avaliação: %.2f, Média de votos: %.2f%n",
                        genre, avgRating, avgVotes);
            }
        }
    }

    private void compareHorrorGenre(Map<Genres, Double> averageRatings, Map<Genres, Double> averageNumVotes) {
        Genres horrorGenre = Genres.HORROR;
        Double horrorAvgRating = averageRatings.get(horrorGenre);
        Double horrorAvgVotes = averageNumVotes.get(horrorGenre);

        System.out.printf("\nGênero Terror - Avaliação média: %.2f, Média de votos: %.2f%n",
                horrorAvgRating, horrorAvgVotes);

        List<Map.Entry<Genres, Double>> topGenresByRating = averageRatings.entrySet().stream()
                .sorted(Map.Entry.<Genres, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());

        System.out.println("\nTop 3 Gêneros por Avaliação Média:");
        for (Map.Entry<Genres, Double> entry : topGenresByRating) {
            Genres genre = entry.getKey();
            Double avgRating = entry.getValue();
            Double avgVotes = averageNumVotes.get(genre);
            System.out.printf("Gênero: %s, Avaliação Média: %.2f, Média de Votos: %.2f%n",
                    genre, avgRating, avgVotes);
        }

        System.out.println("\nComparação da Média de Votos do Terror com os Gêneros de Melhor Avaliação:");
        boolean receivesFewerVotes = true;
        for (Map.Entry<Genres, Double> entry : topGenresByRating) {
            Genres genre = entry.getKey();
            Double avgVotes = averageNumVotes.get(genre);
            System.out.printf("Gênero: %s, Média de Votos: %.2f%n", genre, avgVotes);
            if (horrorAvgVotes >= avgVotes) {
                receivesFewerVotes = false;
            }
        }

        if (receivesFewerVotes) {
            System.out.println("\nConclusão: O gênero Terror recebe menos votos do que os gêneros com melhores avaliações.");
        } else {
            System.out.println("\nConclusão: O gênero Terror não recebe menos votos do que todos os gêneros com melhores avaliações.");
        }
    }

    private void calculateAndInterpretCorrelation(List<Movie> allMovies) {
        double correlation = movieService.calculateCorrelationBetweenVotesAndRatings(allMovies);
        String interpretation = StatisticUtils.interpretCorrelation(correlation);

        System.out.printf("\nCorrelação entre o número médio de votos e a avaliação média: %.4f%n", correlation);
        System.out.println("Interpretação da correlação: " + interpretation);
    }

    public void analyzeHorrorRatingsOverTime(List<Movie> movieList) {
        SimpleRegression regression = new SimpleRegression();

        for (Movie movie : movieList) {
            regression.addData(movie.getReleaseYear(), movie.getAverageRating());
        }

        double slope = regression.getSlope();
        double intercept = regression.getIntercept();
        double rSquared = regression.getRSquare();
        double pValue = regression.getSignificance();

        String trend;
        if (pValue < 0.05) {
            if (slope > 0) {
                trend = "aumentou";
            } else if (slope < 0) {
                trend = "diminuiu";
            } else {
                trend = "permaneceu constante";
            }
            System.out.printf("\nA avaliação média %s ao longo dos anos (p = %.4f).%n", trend, pValue);
        } else {
            System.out.printf("\nNão há uma tendência estatisticamente significativa nas avaliações médias ao longo dos anos (p = %.4f).%n", pValue);
        }

        System.out.printf("Coeficiente Angular: %.4f, Coeficiente Linear: %.4f, R²: %.4f%n", slope, intercept, rSquared);

        FileUtils.exportAverageRatingsToCSV(movieList, "avaliacao_media_por_ano.csv");
    }
}

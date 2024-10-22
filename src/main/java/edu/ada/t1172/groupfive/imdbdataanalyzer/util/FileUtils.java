package edu.ada.t1172.groupfive.imdbdataanalyzer.util;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileUtils {
    public static void exportAverageRatingsToCSV(List<Movie> movieList, String filename) {
        Map<Integer, List<Movie>> moviesByYear = movieList.stream()
                .collect(Collectors.groupingBy(Movie::getReleaseYear));

        Map<Integer, Double> averageRatingsByYear = moviesByYear.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .mapToDouble(Movie::getAverageRating)
                                .average()
                                .orElse(0.0)
                ));

        List<Integer> years = new ArrayList<>(averageRatingsByYear.keySet());
        Collections.sort(years);

        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("Ano,AvaliacaoMedia");
            for (Integer year : years) {
                Double avgRating = averageRatingsByYear.get(year);
                writer.printf("%d,%.2f%n", year, avgRating);
            }
            System.out.println("Dados exportados para: " + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

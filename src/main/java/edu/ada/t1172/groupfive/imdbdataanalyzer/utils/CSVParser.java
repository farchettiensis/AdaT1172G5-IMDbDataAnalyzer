package edu.ada.t1172.groupfive.imdbdataanalyzer.utils;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class CSVParser {
    private static final Logger logger = Logger.getLogger(CSVParser.class.getName());
    private String csvPath;

    public CSVParser(String csvPath) {
        this.csvPath = csvPath;
    }

    public String getCSVPath() {
        return csvPath;
    }

    public void setCSVPath(String csvPath) {
        this.csvPath = csvPath;
    }


    public List<Movie> getAllMovies() throws IOException {
        List<Movie> moviesList = new ArrayList<>();
        try (var lines = Files.lines(Paths.get(csvPath))) {
            lines.skip(1)
                    .map(line -> line.split(";"))
                    .map(data -> new Movie(
                            data[0],
                            data[1],
                            parseGenres(data[2]),
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4]),
                            Integer.parseInt(data[5])
                    ))
                    .forEach(moviesList::add);
        }

        return moviesList;
    }

    private Set<Genres> parseGenres(String genres) {
        Set<Genres> genresSet = new HashSet<>();
        String[] genresArray = genres.replace("\"", "")
                .replace(" ", "")
                .replace("-", "_")
                .split(",");
        for (String genre : genresArray) {
            try {
                genresSet.add(Genres.valueOf(genre.toUpperCase()));
            } catch (IllegalArgumentException e) {
                genresSet.add(Genres.UNKNOWN);
                logger.warning("Gênero desconhecido: " + genre);
            }
        }
        return genresSet;
    }
}

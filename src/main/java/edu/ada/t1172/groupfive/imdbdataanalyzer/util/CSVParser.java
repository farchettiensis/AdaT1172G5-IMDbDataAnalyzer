package edu.ada.t1172.groupfive.imdbdataanalyzer.util;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import edu.ada.t1172.groupfive.imdbdataanalyzer.model.enums.Genres;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO: Verificar se há mais a se implementar
public class CSVParser {
    String caminhoCSV;

    public CSVParser(String caminhoCSV) {
        this.caminhoCSV = caminhoCSV;
    }

    public String getCaminhoCSV() {
        return caminhoCSV;
    }

    public List<Movie> buscarTodos() {
        List<Movie> listaDeFilmes = new ArrayList<>();
        try {
            Files.lines(Paths.get(caminhoCSV))
                    .skip(1)
                    .map(linha -> linha.split(";"))
                    .map(dados -> new Movie(dados[0],dados[1], getGenres(dados[2]),Double.parseDouble(dados[3]),Integer.parseInt(dados[4]),Integer.parseInt(dados[5])))
                    .forEach(listaDeFilmes::add);

            return listaDeFilmes;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Genres> getGenres(String genres) {
        Set<Genres> listaGeneros = new HashSet<>();
        String[] generosString = genres.replace("\"","")
                .replace(" ","")
                .replace("-","_")
                .split(",");
        for (String genero : generosString) {
            listaGeneros.add(Genres.valueOf(genero.toUpperCase()));
        }
        return listaGeneros;
    }
}

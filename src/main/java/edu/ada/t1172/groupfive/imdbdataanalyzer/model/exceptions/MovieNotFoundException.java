package edu.ada.t1172.groupfive.imdbdataanalyzer.model.exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) {
        super("movie not found");
    }
}

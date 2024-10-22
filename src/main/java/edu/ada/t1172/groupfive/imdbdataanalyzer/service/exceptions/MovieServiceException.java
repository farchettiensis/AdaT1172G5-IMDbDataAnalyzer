package edu.ada.t1172.groupfive.imdbdataanalyzer.service.exceptions;

public class MovieServiceException extends RuntimeException {

    public MovieServiceException(String msg) {
        super(msg);
    }

    public MovieServiceException(String msg, Throwable cause) {
        super(msg,cause);
    }
}

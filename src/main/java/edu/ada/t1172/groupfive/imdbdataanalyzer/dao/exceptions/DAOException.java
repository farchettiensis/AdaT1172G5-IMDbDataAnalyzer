package edu.ada.t1172.groupfive.imdbdataanalyzer.dao.exceptions;

public class DAOException extends RuntimeException {

    public DAOException(String msg) {
        super(msg);
    }

    public DAOException(String msg, Throwable cause) {
        super(msg,cause);
    }
}

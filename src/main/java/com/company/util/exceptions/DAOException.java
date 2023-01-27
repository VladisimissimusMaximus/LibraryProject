package com.company.util.exceptions;

public class DAOException extends RuntimeException {
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }
}

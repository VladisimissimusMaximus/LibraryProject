package com.company.util.exceptions;

import java.sql.SQLException;

public class DAOException extends RuntimeException {
    public DAOException() {
    }
    protected DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public static DAOException wrap(SQLException e, String message) {
        DAOException re;
        if (e.getMessage() != null && e.getMessage().contains("Duplicate")) {
            re = new DuplicateFieldException(e);
        } else {
            re = new DAOException(message, e);
        }
        return re;
    }

}

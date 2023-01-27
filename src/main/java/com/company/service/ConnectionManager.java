package com.company.service;

import com.company.util.exceptions.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URI = "jdbc:mysql://localhost:3306/librarydb";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("for name error");
        }
    }

    public static synchronized Connection getConnection() {
        LOGGER.debug("Getting DB connection");
        Connection con = null;
        try {
            con = DriverManager.getConnection(URI,
                    "root", "Vladius92vvv");
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve DB Connection from pool", e);
            throw new ConnectionException("Failed to retrieve DB Connection from pool", e);
        }
        return con;

    }
}

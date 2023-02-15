package com.company.dao;

import com.company.util.exceptions.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
    private static final DataSource dataSource = initDataSource();

    private ConnectionManager() {}

    private static DataSource initDataSource() {
        LOGGER.debug("Retrieving DataSource from context");
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            return (DataSource)envContext.lookup("jdbc/librarydb");
        } catch (NamingException e) {
            LOGGER.error("Failed to obtain instance of DataSource", e);
            throw new ConnectionException("failed to obtain instance of DataSource", e);
        }
    }

    public static synchronized Connection getConnection() {
        LOGGER.debug("Getting DB connection");
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Failed to obtain a connection from DataSource", e);
            throw new ConnectionException("Failed to obtain a connection from DataSource", e);
        }
        return connection;
    }
}

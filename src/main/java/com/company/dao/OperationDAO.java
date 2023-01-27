package com.company.dao;

import com.company.model.*;
import com.company.service.ConnectionManager;
import com.company.util.exceptions.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class OperationDAO {
    private static final String USER_ID_COL = "user_id";
    private static final String BOOK_ID_COL = "book_id";
    private static final String START_DATE_COL = "start_date";
    private static final String TYPE_COL = "type";
    private static final String DURATION_COL = "duration";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    public List<Operation> getByUserId(int userId) {
        LOGGER.info("finding operations with userId {}", userId);

        List<Operation> result = new ArrayList<>();
        String query = "SELECT * FROM book_operations " +
                "WHERE user_id = ?";
        User user = new User();
        user.setId(userId);

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt(BOOK_ID_COL);
                LocalDateTime startDate = rs.getTimestamp(START_DATE_COL).toLocalDateTime();
                OperationStatus type = OperationStatus.valueOf(rs.getString(TYPE_COL));
                int duration = rs.getInt(TYPE_COL);

                Operation operation = new Operation();
                operation.setUser(user);

                Book book = new Book();
                book.setId(bookId);
                operation.setBook(book);

                operation.setStartDate(startDate);
                operation.setStatus(type);
                operation.setDuration(duration);

                result.add(operation);
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find operations with userId {}, cause: {}", userId, e.getMessage());
            throw new DAOException("Failed to find operations with userId " + userId, e);
        }

        return result;
    }

    public List<Operation> getByBookId(int bookId) {
        LOGGER.info("finding operations with bookId {}", bookId);

        String query = "SELECT * FROM book_operations " +
                "WHERE book_id = ?";
        List<Operation> result = new ArrayList<>();
        Book book = new Book();
        book.setId(bookId);

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt(USER_ID_COL);
                LocalDateTime startDate = rs.getTimestamp(START_DATE_COL).toLocalDateTime();
                OperationStatus type = OperationStatus.valueOf(rs.getString(TYPE_COL));
                int duration = rs.getInt(TYPE_COL);

                Operation operation = new Operation();
                operation.setBook(book);

                User user = new User();
                user.setId(userId);
                operation.setUser(user);

                operation.setStartDate(startDate);
                operation.setStatus(type);
                operation.setDuration(duration);

                result.add(operation);
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find operations with bookId {}, cause: {}", bookId, e.getMessage());
            throw new DAOException("Failed to find operations with bookId " + bookId, e);
        }

        return result;
    }

    public List<Operation> findByUserAndBookId(int userId, int bookId) {
        LOGGER.info("finding operations with userId {}", userId);

        List<Operation> result = new ArrayList<>();
        String query = "SELECT * FROM book_operations " +
                "WHERE user_id = ? AND book_id = ?";
        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDateTime startDate = rs.getTimestamp(START_DATE_COL).toLocalDateTime();
                OperationStatus type = OperationStatus.valueOf(rs.getString(TYPE_COL));
                int duration = rs.getInt(TYPE_COL);

                Operation operation = new Operation();
                operation.setUser(user);
                operation.setBook(book);

                operation.setStartDate(startDate);
                operation.setStatus(type);
                operation.setDuration(duration);

                result.add(operation);
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find operations with userId {}, cause: {}", userId, e.getMessage());
            throw new DAOException("Failed to find operations with userId " + userId, e);
        }

        return result;
    }

    public boolean insertOperation(Operation operation) {
        LOGGER.info("inserting the operation `{}`", operation);
        boolean successful = false;
        Connection con = ConnectionManager.getConnection();
        String insertOperation = "INSERT INTO book_operations (user_id, book_id, status, duration_days) " +
                "VALUES (?, ?, ?, ?)";
        String updateBookAvailable = "UPDATE books SET count = count - 1 WHERE id = ?";

        try (
                PreparedStatement insert = con.prepareStatement(insertOperation);
                PreparedStatement update = con.prepareStatement(updateBookAvailable);
        ) {
            con.setAutoCommit(false);
            insert.setInt(1, operation.getUser().getId());
            insert.setInt(2, operation.getBook().getId());
            insert.setString(3, String.valueOf(operation.getStatus()));
            insert.setInt(4, operation.getDuration());
            successful = insert.executeUpdate() > 0;

            update.setInt(1, operation.getBook().getId());
            successful = successful && update.executeUpdate() > 0;
            con.commit();

        } catch (SQLException e) {
            LOGGER.warn("Failed to insert operation `{}`, cause: {}", operation, e.getMessage());
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException excep) {
                    LOGGER.warn("Failed to insert operation `{}`, cause: {}", operation, e.getMessage());
                }
            }
            throw new DAOException("Failed to insert operation", e);
        }

        return successful;
    }

    public boolean delete(Operation operation) {
        LOGGER.info("deleting operation by book id {}", operation.getBook());
        Connection con = ConnectionManager.getConnection();
        boolean successful = false;
        String deleteOperation = "DELETE FROM book_operations " +
                "WHERE book_operations.book_id = ? AND book_operations.user_id = ?";
        String updateBookAvailable = "UPDATE books SET available = available + 1 WHERE id = ?";
        try (
             PreparedStatement delete = con.prepareStatement(deleteOperation);
             PreparedStatement update = con.prepareStatement(updateBookAvailable);
        ) {
            con.setAutoCommit(false);

            delete.setInt(1, operation.getBook().getId());
            delete.setInt(2, operation.getUser().getId());
            successful = delete.executeUpdate() > 0;

            update.setInt(1, operation.getBook().getId());
            successful = successful && update.executeUpdate() > 0;

            con.commit();

        } catch (SQLException e) {
            LOGGER.warn("Failed to delete operation `{}`, cause: {}", operation, e.getMessage());
            if (con != null) {
                try {
                    con.rollback();
                    con.close();
                } catch (SQLException excep) {
                    LOGGER.warn("Failed to delete operation `{}`, cause: {}", operation, e.getMessage());
                }
            }
            throw new DAOException("Failed to delete operation", e);
        }

        return successful;
    }

    public boolean update(Operation operation) {
        LOGGER.info("updating the operation `{}`", operation);

        boolean successful = false;
        String query = "UPDATE book_operations SET status = ?, start_date = ?" +
                "  WHERE book_id = ? AND user_id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, operation.getStatus().toString());
            statement.setTimestamp(2, Timestamp.valueOf(operation.getStartDate()));
            statement.setInt(3, operation.getBook().getId());
            statement.setInt(4, operation.getUser().getId());

            successful = statement.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.warn("Failed to update operation `{}`, cause: {}", operation, e.getMessage());
            throw new DAOException("Failed to update operation", e);
        }
        return successful;
    }


}

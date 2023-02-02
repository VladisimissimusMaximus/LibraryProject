package com.company.dao;

import com.company.model.User;
import com.company.model.UserRole;
import com.company.service.ConnectionManager;
import com.company.util.exceptions.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static final String NAME_COL = "name";
    public static final String PASSWORD_COL = "password";
    public static final String ID_COL = "id";
    public static final String ENABLED_COL = "enabled";
    public static final String REGISTERED_COL = "registered";
    public static final String ROLE_ID_COL = "role_id";
    public static final String EMAIL_COL = "email";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    public List<User> findAll() {

        LOGGER.info("finding all users");
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM users";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt(ID_COL);
                String name = rs.getString(NAME_COL);
                String email = rs.getString(EMAIL_COL);
                String password = rs.getString(PASSWORD_COL);
                Boolean enabled = rs.getBoolean(ENABLED_COL);
                LocalDateTime registered = rs.getTimestamp(REGISTERED_COL).toLocalDateTime();
                int role = rs.getInt(ROLE_ID_COL);
                User user = new User();

                user.setId(id);
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setEnabled(enabled);
                user.setRegistered(registered);
                user.setRole(UserRole.byId(role));
                users.add(user);
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find users {}", e.getMessage());
            throw new DAOException("Failed to find users" + e);
        }

        return users;
    }

    public User findById(int id) {

        LOGGER.info("finding user with id {}", id);

        String query = "SELECT * FROM users WHERE id = ?";
        User result = new User();
        result.setId(id);

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString(NAME_COL);
                String email = rs.getString(EMAIL_COL);
                String password = rs.getString(PASSWORD_COL);
                Boolean enabled = rs.getBoolean(ENABLED_COL);
                LocalDateTime registered = rs.getTimestamp(REGISTERED_COL).toLocalDateTime();
                int role = rs.getInt(ROLE_ID_COL);

                result.setName(name);
                result.setEmail(email);
                result.setPassword(password);
                result.setEnabled(enabled);
                result.setRegistered(registered);
                result.setRole(UserRole.byId(role));
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find user with id {}, cause: {}", id, e.getMessage());
            throw new DAOException("Failed to find user with id " + id, e);
        }

        return result;
    }

    public User findByEmail(String email) {

        LOGGER.info("finding user with email {}", email);

        String query = "SELECT * FROM users WHERE email = ?";
        User result = new User();
        result.setEmail(email);

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString(NAME_COL);
                Integer id  = rs.getInt(ID_COL);
                String password = rs.getString(PASSWORD_COL);
                Boolean enabled = rs.getBoolean(ENABLED_COL);
                LocalDateTime registered = rs.getTimestamp(REGISTERED_COL).toLocalDateTime();
                int role = rs.getInt(ROLE_ID_COL);

                result.setName(name);
                result.setId(id);
                result.setPassword(password);
                result.setEnabled(enabled);
                result.setRegistered(registered);
                result.setRole(UserRole.byId(role));
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find user with email {}, cause: {}", email, e.getMessage());
            throw new DAOException("Failed to find user with email " + email, e);
        }

        return result;
    }

    public void insert(User user) {
        LOGGER.info("creating new user with email `{}`", user.getEmail());
        ResultSet resultSet = null;

        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            if (stmt.executeUpdate() == 1) {
                resultSet = stmt.getGeneratedKeys();
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn("Failed to create user `{}`, cause: {}", user.getEmail(), e.getMessage());
            throw new DAOException("Failed to create user", e);
        }
    }

    public void delete(int id) {
        LOGGER.info("deleting user by id {}", id);
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement statement = con.prepareStatement(query);
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.warn("Failed to delete user `{}`, cause: {}", id, e.getMessage());
            throw new DAOException("Failed to delete user", e);
        }
    }

    public boolean update(User user) {

        LOGGER.info("Updating user with id `{}`", user.getId());

        boolean successful = false;
        String query = "UPDATE users SET name = ?, email = ?, role_id = ?" +
                "  WHERE id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setInt(3, user.getRole().getId());
            statement.setInt(4, user.getId());

            successful = statement.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.warn("Failed to update user `{}`, cause: {}", user.getEmail(), e.getMessage());
            throw new DAOException("Failed to update user", e);
        }
        return successful;
    }

    public boolean updateEnabled(Integer userId, boolean enabled) {
        LOGGER.info("Updating 'enabled' field for user with id `{}` to {}", userId, enabled);

        boolean successful = false;
        String query = "UPDATE users SET enabled = ?" +
                "  WHERE id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setBoolean(1, enabled);
            statement.setInt(2, userId);

            successful = statement.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.warn("Failed to update user 'enabled' , cause: {}", e.getMessage());
            throw new DAOException("Failed to update user 'enabled'", e);
        }
        return successful;
    }

}

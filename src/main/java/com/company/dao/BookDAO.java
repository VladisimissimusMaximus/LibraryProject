package com.company.dao;

import com.company.service.ConnectionManager;
import com.company.model.Book;
import com.company.util.exceptions.DAOException;
import com.company.util.selection.Filter;
import com.company.util.selection.SelectionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookDAO {
    public static final String NAME_COL = "name";
    public static final String AUTHOR_COL = "author";
    public static final String PUBLISHER_COL = "publisher";
    public static final String ID_COL = "id";
    public static final String PUBLICATION_DATE_COL = "publication_date";
    public static final String COUNT_COL = "count";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    public List<Book> findAll(SelectionOptions options) {
        LOGGER.info("getting all books with options {}", options);

        String query = "SELECT * FROM books WHERE books.count > 0";
        query = options.applyToQuery(query);
        List<Book> books = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            Map<Filter.FilterColumn, String> filterColumnValues = options.getFilterColumnValues();
            int index = 1;
            for (Map.Entry<Filter.FilterColumn, String> entry : filterColumnValues.entrySet()) {
                stmt.setString(index++, entry.getValue());
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                int id = rs.getInt(ID_COL);
                String name = rs.getString(NAME_COL);
                String author = rs.getString(AUTHOR_COL);
                String publisher = rs.getString(PUBLISHER_COL);
                int count = rs.getInt(COUNT_COL);
                LocalDateTime publicationDateTime = rs.getTimestamp(PUBLICATION_DATE_COL).toLocalDateTime();
                book.setId(id);
                book.setName(name);
                book.setAuthor(author);
                book.setPublisher(publisher);
                book.setCount(count);
                book.setPublicationDate(publicationDateTime.toLocalDate());
                books.add(book);
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find all books, cause: {}", e.getMessage());
            throw new DAOException("Failed to find all books " , e);
        }

        return books;
    }

    public List<Book> findAllByName(String name, SelectionOptions options) {
        LOGGER.info("getting all books by name {}", name);

        String query = "SELECT * FROM books WHERE books.count > 0 AND books.name = ?";
        query = options.applyToQuery(query);
        List<Book> books = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                int id = rs.getInt(ID_COL);
                String author = rs.getString(AUTHOR_COL);
                String publisher = rs.getString(PUBLISHER_COL);
                int count = rs.getInt(COUNT_COL);
                LocalDateTime publicationDateTime = rs.getTimestamp(PUBLICATION_DATE_COL).toLocalDateTime();
                book.setId(id);
                book.setName(name);
                book.setAuthor(author);
                book.setPublisher(publisher);
                book.setCount(count);
                book.setPublicationDate(publicationDateTime.toLocalDate());
                books.add(book);
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find all books, cause: {}", e.getMessage());
            throw new DAOException("Failed to find all books " , e);
        }

        return books;
    }

    public List<Book> findAllByAuthor(String author, SelectionOptions options) {
        LOGGER.info("getting all books by author {}", author);

        String query = "SELECT * FROM books WHERE books.count > 0 AND books.author = ?";
        query = options.applyToQuery(query);
        List<Book> books = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, author);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                int id = rs.getInt(ID_COL);
                String name = rs.getString(NAME_COL);
                String publisher = rs.getString(PUBLISHER_COL);
                int count = rs.getInt(COUNT_COL);
                LocalDateTime publicationDateTime = rs.getTimestamp(PUBLICATION_DATE_COL).toLocalDateTime();
                book.setId(id);
                book.setName(name);
                book.setAuthor(author);
                book.setPublisher(publisher);
                book.setCount(count);
                book.setPublicationDate(publicationDateTime.toLocalDate());
                books.add(book);
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find all books, cause: {}", e.getMessage());
            throw new DAOException("Failed to find all books " , e);
        }

        return books;
    }

    public Book findById(int id) {

        LOGGER.info("finding book with id {}", id);

        String query = "SELECT * FROM books WHERE id = ?";
        Book result = new Book();
        result.setId(id);

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString(NAME_COL);
                String author = rs.getString(AUTHOR_COL);
                String publisher = rs.getString(PUBLISHER_COL);
                int count = rs.getInt(COUNT_COL);
                LocalDateTime publicationDateTime = rs.getTimestamp(PUBLICATION_DATE_COL).toLocalDateTime();

                result.setName(name);
                result.setAuthor(author);
                result.setPublisher(publisher);
                result.setCount(count);
                result.setPublicationDate(publicationDateTime.toLocalDate());
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find book with id {}, cause: {}", id, e.getMessage());
            throw new DAOException("Failed to find book with id " + id, e);
        }

        return result;
    }

    public Book findByName(String name) {

        LOGGER.info("finding book with id {}", name);

        String query = "SELECT * FROM books WHERE name = ?";
        Book result = new Book();
        result.setName(name);

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt(ID_COL);
                String author = rs.getString(AUTHOR_COL);
                String publisher = rs.getString(PUBLISHER_COL);
                int count = rs.getInt(COUNT_COL);
                LocalDateTime publicationDateTime = rs.getTimestamp(PUBLICATION_DATE_COL).toLocalDateTime();

                result.setId(id);
                result.setAuthor(author);
                result.setPublisher(publisher);
                result.setCount(count);
                result.setPublicationDate(publicationDateTime.toLocalDate());
            }

        } catch (SQLException e) {
            LOGGER.warn("Failed to find book with name {}, cause: {}", name, e.getMessage());
            throw new DAOException("Failed to find book with name " + name, e);
        }

        return result;
    }


    public void insert(Book book) {
        LOGGER.info("creating new book with id `{}`", book.getId());
        String query = "INSERT INTO books (name, author, publisher, publication_date, count) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query,  Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, book.getName());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setString(4, String.valueOf(book.getPublicationDate()));
            stmt.setInt(5, book.getCount());
            stmt.executeUpdate();

        } catch(SQLException e){
            LOGGER.warn("Failed to create book `{}`, cause: {}", book.getId(), e.getMessage());
            throw new DAOException("Failed to create book", e);
        }
    }

    public boolean delete(int id) {
        boolean successful = false;
        String query = "DELETE FROM books WHERE books.id = ?";
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement statement = con.prepareStatement(query);
        ) {
            statement.setInt(1, id);
            successful = statement.executeUpdate() > 0;

        } catch (SQLException e) {
            //here must have been log
            System.out.println("ERROR, can't delete book " + id +
                    "reason: " + e.getMessage());
        }

        return successful;
    }

    public boolean update(Book book) {

        LOGGER.info("Updating book with id `{}`", book.getId());

        boolean successful = false;
        String query  = "UPDATE books SET name = ?, author = ?, publisher = ?, publication_date = ?, count = ?" +
                " WHERE id = ?";
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement statement = con.prepareStatement(query)){
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setString(4, String.valueOf(book.getPublicationDate()));
            statement.setInt(5, book.getCount());
            statement.setInt(6, book.getId());

            successful = statement.executeUpdate() > 0;

        } catch(SQLException e){
            LOGGER.warn("Failed to update book `{}`, cause: {}", book.getId(), e.getMessage());
            throw new DAOException("Failed to update book", e);
        }
        return successful;
    }

    public int findCount() {
        LOGGER.info("find count of books");
        String query = "SELECT count(id) FROM books WHERE books.count > 0";
        int result = 0;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("failed to find count of books", e);
        }
        LOGGER.info("count is {}", result);
        return result;
    }

    public int findCount(Filter filter) {
        LOGGER.info("find count of books with filter {}" + filter.toString());
        String query = "SELECT count(id) FROM books WHERE books.count > 0";
        query = filter.applyToSQLQuery(query);
        int result = 0;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ) {

            Map<Filter.FilterColumn, String> filterColumnValues = filter.getColumnValueMap();
            int index = 1;
            for (Map.Entry<Filter.FilterColumn, String> entry : filterColumnValues.entrySet()) {
                stmt.setString(index++, entry.getValue());
            }

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("failed to find count of books", e);
        }
        LOGGER.info("count is {}", result);
        return result;
    }



}

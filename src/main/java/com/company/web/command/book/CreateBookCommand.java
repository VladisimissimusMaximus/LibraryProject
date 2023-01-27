package com.company.web.command.book;

import com.company.dao.BookDAO;
import com.company.model.Book;
import com.company.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateBookCommand extends AbstractCommand {
    private final BookDAO bookDAO = new BookDAO();


    @Override
    public void process() throws ServletException, IOException {
        Book book = new Book();
        book.setId(Integer.parseInt(req.getParameter("id")));
        book.setName(req.getParameter("name"));
        book.setAuthor(req.getParameter("author"));
        book.setPublisher(req.getParameter("publisher"));
        book.setPublicationDate(LocalDate.parse(req.getParameter("publication_date")));

        //TODO finish this, this is POST so use SubmitRegister as an example
    }
}

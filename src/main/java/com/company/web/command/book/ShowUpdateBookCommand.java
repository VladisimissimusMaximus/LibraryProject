package com.company.web.command.book;

import com.company.model.Book;
import com.company.service.BookService;
import com.company.util.WebUtil;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowUpdateBookCommand extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowUpdateBookCommand.class);
    public static final BookService service = new BookService();
    private Integer bookId;

    @Override
    public void init(HttpServletRequest req, HttpServletResponse res) {
        super.init(req, res);
        bookId = WebUtil.parseIdFromUri(req);
    }

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.info("start getting book {}", bookId);

        Book book = service.getById(bookId);
        req.setAttribute("name", book.getName());
        req.setAttribute("author", book.getAuthor());
        req.setAttribute("publisher", book.getPublisher());
        req.setAttribute("publicationDate", book.getPublicationDate());
        req.setAttribute("count", book.getCount());
        req.setAttribute("action", "update");

        WebUtil.forward(req, resp, View.SUBMIT_BOOK);
    }






}

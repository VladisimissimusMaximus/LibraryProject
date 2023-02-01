package com.company.web.command.book;

import com.company.model.Book;
import com.company.service.BookService;
import com.company.util.WebUtil;
import com.company.util.exceptions.BookValidationException;
import com.company.web.Uri;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class SubmitUpdateBookCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmitUpdateBookCommand.class);
    private static final BookService service = new BookService();
    private Integer bookId;

    @Override
    public void init(HttpServletRequest req, HttpServletResponse resp) {
        super.init(req, resp);
        bookId = WebUtil.parseIdFromUri(req);
    }

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.info("start submitting book update {}", bookId);

        Book book = new Book();

        book.setId(bookId);
        book.setName(req.getParameter("name"));
        book.setAuthor(req.getParameter("author"));
        book.setPublisher(req.getParameter("publisher"));
        book.setPublicationDate(LocalDate.parse(req.getParameter("publicationDate")));
        book.setCount(Integer.parseInt(req.getParameter("count")));

        try {
            service.update(book);
            resp.sendRedirect(Uri.CATALOGUE.toAbsolutePath(req.getContextPath()));

        } catch (BookValidationException e) {
            LOGGER.debug("message: error occurred updating book {} cause {}", bookId, e.getCause());
            req.setAttribute("errorCode", "error.validationError");
            req.setAttribute("nameValidation", e.getNameValidation());
            req.setAttribute("authorValidation", e.getAuthorValidation());
            req.setAttribute("publisherValidation", e.getPublisherValidation());
            req.setAttribute("publicationDateValidation", e.getPublicationDateValidation());
            req.setAttribute("countValidation", e.getCountValidation());
            req.setAttribute("action", "update");
            WebUtil.forward(req, resp, View.SUBMIT_BOOK);
        }

    }
}

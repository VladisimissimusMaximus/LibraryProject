package com.company.web.command.book;

import com.company.dao.BookDAO;
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
import java.io.IOException;
import java.time.LocalDate;

public class SubmitCreateBookCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(SubmitCreateBookCommand.class);
    private final BookService service = new BookService();


    @Override
    public void process() throws ServletException, IOException {
        Book book = new Book();
        book.setId(Integer.parseInt(req.getParameter("id")));
        book.setName(req.getParameter("name"));
        book.setAuthor(req.getParameter("author"));
        book.setPublisher(req.getParameter("publisher"));
        book.setPublicationDate(LocalDate.parse(req.getParameter("publication_date")));


        try {
            service.create(book);
            resp.sendRedirect(Uri.CREATE_BOOK.getPath());

        } catch (BookValidationException e) {
            logger.debug("message: error occurred while creating book {} cause {}", e, e.getCause());
            req.setAttribute("errorCode", "error.validationError");
            req.setAttribute("authorValidation", e.getAuthorValidation());
            req.setAttribute("nameValidation", e.getNameValidation());
            req.setAttribute("publisherValidation", e.getPublisherValidation());
            req.setAttribute("publicationDateValidation", e.getPublicationDateValidation());
            WebUtil.forward(req, resp, View.CREATE_BOOK);
        }


    }
}

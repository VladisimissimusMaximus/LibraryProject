package com.company.web.command.book;

import com.company.service.BookService;
import com.company.util.ApplicationContainer;
import com.company.util.WebUtil;
import com.company.web.Uri;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteBookCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(DeleteBookCommand.class);
    private static final BookService service = ApplicationContainer.getContainer().getBookService();
    private Integer bookId;

    @Override
    public void init(HttpServletRequest req, HttpServletResponse res) {
        super.init(req, res);
        bookId = WebUtil.parseIdFromUri(req);
    }

    @Override
    public void process() throws ServletException, IOException {
        logger.info("start deleting book with id {}", bookId);
        service.deleteById(bookId);
        resp.sendRedirect(Uri.CATALOGUE.toAbsolutePath(req.getContextPath()));
    }

}

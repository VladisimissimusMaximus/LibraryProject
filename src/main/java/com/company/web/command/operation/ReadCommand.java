package com.company.web.command.operation;

import com.company.model.Book;
import com.company.model.User;
import com.company.service.OperationService;
import com.company.util.ApplicationContainer;
import com.company.util.WebUtil;
import com.company.util.exceptions.OperationValidationException;
import com.company.web.Uri;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReadCommand extends AbstractCommand {
    public static final Logger logger = LoggerFactory.getLogger(SubmitSubscribeCommand.class);
    private static final OperationService service =
            ApplicationContainer.getContainer().getOperationService();
    private Integer bookId;
    private Integer userId;

    @Override
    public void init(HttpServletRequest req, HttpServletResponse res) {
        super.init(req, res);
        bookId = WebUtil.parseIdFromUri(req);
        userId = (Integer) req.getSession(false).getAttribute("userId");
    }

    @Override
    public void process() throws ServletException, IOException {
        logger.info("start place a subscription order on book {} for user {}", bookId, userId);

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);

        try {
            service.takeToReadingRoom(user, book);
            resp.sendRedirect(Uri.CATALOGUE.toAbsolutePath(req.getContextPath()));
        } catch (OperationValidationException validationException) {
            logger.error("error taking a book {} to reading room by user {}. Exception: {}",
                    bookId, userId, validationException);

            String queryString = "?errorCode=" + validationException.getDuplicationValidation();
            //TODO add a proper exception handling in case a book was taken concurrently (DB exception)
            resp.sendRedirect(Uri.CATALOGUE.toAbsolutePath(req.getContextPath()) + queryString);
        }


    }
}

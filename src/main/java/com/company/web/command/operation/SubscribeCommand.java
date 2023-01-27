package com.company.web.command.operation;

import com.company.model.Book;
import com.company.model.Operation;
import com.company.model.User;
import com.company.service.OperationService;
import com.company.util.WebUtil;
import com.company.util.exceptions.OperationValidationException;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SubscribeCommand extends AbstractCommand {
    public static final Logger logger = LoggerFactory.getLogger(SubscribeCommand.class);
    private static final OperationService service = new OperationService();
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
        logger.info("start place a subscription order on book {} for user {}", userId, bookId);

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);

        String durationParam = req.getParameter("duration");

        try {
            service.placeOrder(user, book, durationParam);
            // TODO redirect
        } catch(OperationValidationException validationException) {
            req.setAttribute("duration", durationParam);
            req.setAttribute("operationValidation", validationException.getDurationValidation());
            // TODO redirect
        }

    }
}

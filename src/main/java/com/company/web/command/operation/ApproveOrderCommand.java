package com.company.web.command.operation;

import com.company.model.Book;
import com.company.model.User;
import com.company.service.OperationService;
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

public class ApproveOrderCommand extends AbstractCommand {
    public static final Logger logger = LoggerFactory.getLogger(ApproveOrderCommand.class);
    private static final OperationService service =
            ApplicationContainer.getOperationService();
    private Integer bookId;
    private Integer userId;

    @Override
    public void init(HttpServletRequest req, HttpServletResponse res) {
        super.init(req, res);
        userId = Integer.valueOf(req.getParameter("userId"));
        bookId = Integer.valueOf(req.getParameter("bookId"));
    }

    @Override
    public void process() throws ServletException, IOException {
        logger.info("Approving order of user {} for book {}", userId, bookId);

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);

        service.approveOrder(user, book);

        WebUtil.redirectToRefererOrHome(req, resp);
    }
}

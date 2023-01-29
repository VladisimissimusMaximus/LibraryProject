package com.company.web.command.operation;

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

public class ShowSubscribeCommand extends AbstractCommand {
    public static final Logger logger = LoggerFactory.getLogger(ShowSubscribeCommand.class);
    private static final BookService bookService = new BookService();
    private static final Integer DEFAULT_SUBSCRIPTION_COST_DOLLARS = 10;

    private Integer bookId;

    @Override
    public void init(HttpServletRequest req, HttpServletResponse res) {
        super.init(req, res);
        bookId = WebUtil.parseIdFromUri(req);
    }

    @Override
    public void process() throws ServletException, IOException {
        logger.info("start show subscribe on book {}", bookId);

        Book book = bookService.getById(bookId);
        req.setAttribute("title", book.getName());
        req.setAttribute("cost", DEFAULT_SUBSCRIPTION_COST_DOLLARS);

        WebUtil.forward(req, resp, View.SUBSCRIBE);
    }
}

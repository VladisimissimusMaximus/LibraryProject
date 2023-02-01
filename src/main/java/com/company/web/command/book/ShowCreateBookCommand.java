package com.company.web.command.book;

import com.company.util.WebUtil;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowCreateBookCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(ShowCreateBookCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        logger.info("Starting show create book");
        req.setAttribute("action", "create");
        WebUtil.forward(req, resp, View.SUBMIT_BOOK);
    }
}

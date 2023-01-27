package com.company.web.command.user;

import com.company.util.WebUtil;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowProfileUpdateCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowProfileUpdateCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        String userId = req.getSession(false).getAttribute("userId").toString();
        LOGGER.info("Start show profile update page {}", userId);
        req.setAttribute("id", userId);
        WebUtil.forward(req, resp, View.UPDATE_USER);
    }
}

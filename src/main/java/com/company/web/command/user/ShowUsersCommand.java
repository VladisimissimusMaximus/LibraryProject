package com.company.web.command.user;

import com.company.service.UserService;
import com.company.util.ApplicationContainer;
import com.company.util.WebUtil;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowUsersCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowUsersCommand.class);
    private static final UserService service = ApplicationContainer.getContainer().getUserService();
    @Override
    public void process() throws ServletException, IOException {
        LOGGER.info("Starting show users");
        req.setAttribute("users", service.getAllUsers() );
        WebUtil.forward(req, resp, View.USERS);
    }
}

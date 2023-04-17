package com.company.web.command.user;

import com.company.model.User;
import com.company.service.UserService;
import com.company.util.ApplicationContainer;
import com.company.util.WebUtil;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowUserUpdateCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmitUserUpdateCommand.class);
    private static final UserService service = ApplicationContainer.getContainer().getUserService();
    private Integer userId;


    @Override
    public void init(HttpServletRequest req, HttpServletResponse res) {
        super.init(req, res);
        userId = WebUtil.parseIdFromUri(req);
    }

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.info("start getting user {}", userId);

        User user = service.getById(userId);
        req.setAttribute("name", user.getName());
        req.setAttribute("email", user.getEmail());
        req.setAttribute("role", user.getRole());

        WebUtil.forward(req, resp, View.UPDATE_USER);
    }
}

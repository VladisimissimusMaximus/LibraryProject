package com.company.web.command.user;

import com.company.model.User;
import com.company.model.UserRole;
import com.company.service.UserService;
import com.company.util.WebUtil;
import com.company.util.exceptions.UserValidationException;
import com.company.web.Uri;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SubmitUserUpdateCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmitProfileUpdateCommand.class);
    private static final UserService service = new UserService();
    private Integer userId;

    @Override
    public void init(HttpServletRequest req, HttpServletResponse resp) {
        super.init(req, resp);
        userId = WebUtil.parseIdFromUri(req);
    }

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.info("start submitting user update {}", userId);

        User user = new User();

        user.setId(userId);
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        user.setRole(UserRole.valueOf(req.getParameter("role")));

        try {
            service.update(user);
            resp.sendRedirect(Uri.USERS.toAbsolutePath(req.getContextPath()));

        } catch (UserValidationException e) {
            LOGGER.debug("message: error occurred updating user {} cause {}", userId, e.getCause());
            req.setAttribute("errorCode", "error.validationError");
            req.setAttribute("emailValidation", e.getEmailValidation());
            req.setAttribute("nameValidation", e.getNameValidation());
            WebUtil.forward(req, resp, View.UPDATE_USER);
        }

    }
}

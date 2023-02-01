package com.company.web.command.user;

import com.company.model.User;
import com.company.service.UserService;
import com.company.util.WebUtil;
import com.company.util.exceptions.UserValidationException;
import com.company.web.Uri;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import com.company.web.FrontControllerServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

public class SubmitRegisterCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(SubmitRegisterCommand.class);
    private final UserService service = new UserService();

    @Override
    public void process() throws ServletException, IOException {
        User user = new User();
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);

        try {
            service.register(user);
            resp.sendRedirect(Uri.LOGIN.getPath());

        } catch (UserValidationException e) {
            logger.debug("message: error occurred while registering {} cause {}", e, e.getCause());
            req.setAttribute("errorCode", "error.validationError");
            req.setAttribute("emailValidation", e.getEmailValidation());
            req.setAttribute("nameValidation", e.getNameValidation());
            req.setAttribute("passwordValidation", e.getPasswordValidation());
            WebUtil.forward(req, resp, View.REGISTER);
        }


    }
}

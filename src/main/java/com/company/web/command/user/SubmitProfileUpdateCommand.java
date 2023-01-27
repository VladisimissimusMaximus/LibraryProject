package com.company.web.command.user;

import com.company.model.User;
import com.company.model.UserRole;
import com.company.service.UserService;
import com.company.util.UserUtil;
import com.company.util.WebUtil;
import com.company.util.exceptions.UserValidationException;
import com.company.web.Uri;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SubmitProfileUpdateCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmitProfileUpdateCommand.class);
    private static final UserService service = new UserService();
    @Override
    public void process() throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String userId = session.getAttribute("userId").toString();
        String sessionRole = session.getAttribute("userRole").toString();
        User user = new User();

        LOGGER.info("start submitting user update {}", userId);
        user.setEmail(req.getParameter("email"));
        user.setName(req.getParameter("name"));
        user.setId(Integer.valueOf(userId));

        if(UserRole.ADMINISTRATOR.name().equals(sessionRole)){
            user.setRole(UserRole.valueOf(req.getParameter("role")));
        }else
        {
            user.setRole(UserRole.valueOf(sessionRole));
        }

        try {
            service.update(user);
            UserUtil.renewUserSession(req, user);
            resp.sendRedirect(Uri.PROFILE.toAbsolutePath(req.getContextPath()));


        } catch (UserValidationException e) {
            LOGGER.debug("message: error occurred cause ", e.getCause());
            req.setAttribute("errorCode", "error.validationError");
            req.setAttribute("emailValidation", e.getEmailValidation());
            req.setAttribute("nameValidation", e.getNameValidation());
            WebUtil.forward(req, resp, View.UPDATE_USER);
        }
    }
}

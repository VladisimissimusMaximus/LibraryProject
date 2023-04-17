package com.company.web.command.user;

import com.company.model.User;
import com.company.service.UserService;
import com.company.util.ApplicationContainer;
import com.company.util.UserUtil;
import com.company.util.WebUtil;
import com.company.web.Uri;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import com.company.web.FrontControllerServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

import static com.company.util.UserUtil.renewUserSession;

public class SubmitLoginCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(FrontControllerServlet.class);
    private static final UserService service = ApplicationContainer.getContainer().getUserService();

    @Override
    public void process() throws ServletException, IOException {

        String password = req.getParameter("password");
        User user = service.getByEmail(req.getParameter("email"));
        if (UserUtil.checkPasswordsEquals(password, user.getPassword()) && user.getEnabled()) {
            renewUserSession(req, user);
            resp.sendRedirect(Uri.HOME.toAbsolutePath(req.getContextPath()));
        } else {
            logger.warn("wrong passwords OR USER IS DISABLED: user password is " + user.getPassword() + " and provided " + password);
            req.setAttribute("errorCode", "error.wrongCredentials" );
            WebUtil.forward(req, resp, View.LOGIN);
        }
    }

}

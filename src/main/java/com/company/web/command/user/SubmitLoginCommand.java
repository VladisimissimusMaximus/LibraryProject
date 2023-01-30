package com.company.web.command.user;

import com.company.model.User;
import com.company.service.UserService;
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
    UserService service = new UserService();

    @Override
    public void process() throws ServletException, IOException {

        String password = req.getParameter("password");
        User user = service.getByEmail(req.getParameter("email"));
        if (UserUtil.checkPasswordsEquals(password, user.getPassword())) {
                renewUserSession(req, user);
            //   resp.sendRedirect(Uri.HOME.getPath());
            resp.sendRedirect(Uri.HOME.toAbsolutePath(req.getContextPath()));
        } else {
            logger.info("wrong passwords: user password is " + user.getPassword() + " and provided " + password);
            //throw new NotFoundException();
            req.setAttribute("errorCode", "error.wrongCredentials" );
            WebUtil.forward(req, resp, View.LOGIN);
        }
    }

}

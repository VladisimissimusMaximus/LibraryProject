package com.company.web.command.user;

import com.company.util.WebUtil;
import com.company.web.Uri;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShowLoginCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(ShowLoginCommand.class);

    @Override
    public void process() throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Object sessionRole;
        if (session != null && (sessionRole = session.getAttribute("userRole")) != null) {
            logger.info("redirecting user with role {} away from the page", sessionRole.toString());
            resp.sendRedirect(Uri.HOME.toAbsolutePath(req.getContextPath()));
            return;
        }

        WebUtil.forward(req, resp, View.LOGIN);
    }
}

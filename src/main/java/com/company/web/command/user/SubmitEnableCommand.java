package com.company.web.command.user;

import com.company.service.UserService;
import com.company.util.ApplicationContainer;
import com.company.util.WebUtil;
import com.company.web.Uri;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SubmitEnableCommand extends AbstractCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmitEnableCommand.class);
    private static final UserService service = ApplicationContainer.getContainer().getUserService();
    private Integer userId;


    @Override
    public void init(HttpServletRequest req, HttpServletResponse res) {
        super.init(req, res);
        userId = WebUtil.parseIdFromUri(req);
    }

    @Override
    public void process() throws ServletException, IOException {
        LOGGER.info("starting to enable user by id {}", userId);

        service.updateEnabled(userId, true);
        resp.sendRedirect(Uri.USERS.toAbsolutePath(req.getContextPath()));
    }
}

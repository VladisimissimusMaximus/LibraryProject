package com.company.web.command.user;

import com.company.service.UserService;
import com.company.util.WebUtil;
import com.company.web.Uri;
import com.company.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserCommand extends AbstractCommand {
    private Integer userId;
    private static final UserService service = new UserService();

    @Override
    public void init(HttpServletRequest req, HttpServletResponse resp) {
        super.init(req, resp);
        userId = WebUtil.parseIdFromUri(req);
    }

    @Override
    public void process() throws ServletException, IOException {
        service.deleteById(userId);
        resp.sendRedirect(Uri.USERS.toAbsolutePath(req.getContextPath()));
    }
}

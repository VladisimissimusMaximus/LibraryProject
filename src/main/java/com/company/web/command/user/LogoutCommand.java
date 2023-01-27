package com.company.web.command.user;

import com.company.web.Uri;
import com.company.web.View;
import com.company.web.command.AbstractCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class LogoutCommand extends AbstractCommand {
    @Override
    public void process() throws ServletException, IOException {
        req.getSession(false).invalidate();
        resp.sendRedirect(Uri.HOME.getPath());
    }
}

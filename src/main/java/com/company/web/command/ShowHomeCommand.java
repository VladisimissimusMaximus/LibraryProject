package com.company.web.command;

import com.company.web.View;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowHomeCommand extends AbstractCommand {
    @Override
    public void process() throws ServletException, IOException {
        req.getRequestDispatcher(View.HOME.getPath()).forward(req, resp);
    }
}

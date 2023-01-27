package com.company.web.command.user;

import com.company.util.WebUtil;
import com.company.web.View;
import com.company.web.command.AbstractCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowLoginCommand extends AbstractCommand {

    @Override
    public void process() throws ServletException, IOException {
        WebUtil.forward(req, resp, View.LOGIN);
    }
}

package com.company.web.command.user;

import com.company.util.WebUtil;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowProfileCommand extends AbstractCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowProfileCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        WebUtil.forward(req, resp, View.PROFILE);
    }
}

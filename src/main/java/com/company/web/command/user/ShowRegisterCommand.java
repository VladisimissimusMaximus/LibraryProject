package com.company.web.command.user;

import com.company.util.WebUtil;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import com.company.web.FrontControllerServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowRegisterCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(FrontControllerServlet.class);
    @Override
    public void process() throws ServletException, IOException {
        logger.info("Starting show register");
        WebUtil.forward(req, resp, View.REGISTER);
    }
}

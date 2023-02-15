package com.company.web.command.operation;

import com.company.service.OperationService;
import com.company.util.WebUtil;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowReaderOperationsCommand extends AbstractCommand {
    public static final Logger logger = LoggerFactory.getLogger(ShowReaderOperationsCommand.class);
    private static final OperationService service = new OperationService();

    private Integer userId;

    @Override
    public void init(HttpServletRequest req, HttpServletResponse res) {
        super.init(req, res);
        userId = WebUtil.parseIdFromUri(req);
    }

    @Override
    public void process() throws ServletException, IOException {
        logger.info("Start showing all operations for user {}", userId);

        req.setAttribute("operations", service.getAllByUser(userId));
        WebUtil.forward(req, resp, View.MY_OPERATIONS);
    }
}

package com.company.web.command.operation;

import com.company.service.OperationService;
import com.company.util.WebUtil;
import com.company.util.selection.SelectionOptions;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowOperationsCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(ShowOperationsCommand.class);
    private static final OperationService service = new OperationService();

    @Override
    public void process() throws ServletException, IOException {
        logger.info("Start showing all operations");

        SelectionOptions options = WebUtil.parseSelectionOptions(req);
        int totalRecordsCount = service.getCount(options.getFilter());

        WebUtil.appendWithSelectionAttributes(req, options, totalRecordsCount);

        req.setAttribute("operations", service.getAll(options));
        WebUtil.forward(req, resp, View.OPERATIONS);
    }
}

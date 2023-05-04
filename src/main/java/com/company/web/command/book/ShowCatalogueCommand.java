package com.company.web.command.book;

import com.company.model.Book;
import com.company.service.BookService;
import com.company.util.ApplicationContainer;
import com.company.util.WebUtil;
import com.company.util.selection.SelectionOptions;
import com.company.web.View;
import com.company.web.command.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class ShowCatalogueCommand extends AbstractCommand {
    private static final Logger logger = LoggerFactory.getLogger(ShowCatalogueCommand.class);

    public BookService service = ApplicationContainer.getBookService();

    @Override
    public void process() throws ServletException, IOException {
        logger.info("Start getting all books");

        SelectionOptions selectionOptions = WebUtil.parseSelectionOptions(req);

        List<Book> result = service.getAll(selectionOptions);

        req.setAttribute("books", result);

        int totalRecordsCount = service.getCount(selectionOptions.getFilter());

        WebUtil.appendWithSelectionAttributes(req, selectionOptions, totalRecordsCount);
        WebUtil.forward(req, resp, View.CATALOGUE);
    }

}

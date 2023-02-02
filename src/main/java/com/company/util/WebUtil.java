package com.company.util;

import com.company.util.selection.Filter;
import com.company.util.selection.Order;
import com.company.util.selection.Paging;
import com.company.util.selection.SelectionOptions;
import com.company.web.Resource;
import com.company.web.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class WebUtil {
    public static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    private WebUtil() {}

    public static void forward(HttpServletRequest req, HttpServletResponse resp, Resource res)
            throws ServletException, IOException {
        req.getRequestDispatcher(res.getPath()).forward(req, resp);
    }

    public static Integer parseIdFromUri(HttpServletRequest req){
        String uri = req.getRequestURI();
        String lastPart = uri.substring(uri.lastIndexOf('/') + 1);
        return Integer.valueOf(lastPart);
    }

    public static SelectionOptions parseSelectionOptions(HttpServletRequest req) {
        String queryString = req.getQueryString();

        String orderParam = req.getParameter("order");
        String recordsPerPageParam = req.getParameter("recordsPerPage");
        String pageNumberParam = req.getParameter("pageNumber");

        logger.info("selection params retrieved - order: {}, recordsPP: {}, page: {}", orderParam, recordsPerPageParam, pageNumberParam);

        return SelectionOptions.build()
                .withOrder(orderParam)
                .withRecordsPerPage(recordsPerPageParam)
                .withFilter(queryString)
                .withPageNumber(pageNumberParam)
                .build();
    }

    public static void appendWithSelectionAttributes(HttpServletRequest req, SelectionOptions options, int totalCount) {
        logger.info("appending selection attributes to req {}", req.getRequestURI());
        Paging paging = options.getPaging();
        if (paging == null) {
            logger.info("no previous paging info found, using default setting  {}", req.getRequestURI());
            paging = new Paging();
        }

        int recordsPerPage = paging.getRecordsPerPage();
        req.setAttribute("recordsPerPage", recordsPerPage);

        int pagesTotal = (int) Math.ceil(totalCount * 1.0 / recordsPerPage);
        req.setAttribute("pagesTotal", pagesTotal);

        Integer currentPage = paging.getCurrentPage();
        req.setAttribute("currentPage", currentPage);

        Order order = options.getOrder();
        if (order != null) {
            req.setAttribute("order", order.getAttributeValue());
        }

        Filter filter = options.getFilter();
        if (filter != null) {
            filter.getColumnValueMap()
                    .forEach((column, value) -> req.setAttribute(
                            column.getAttributeValue(), value
                    ));
        }
    }

    public static void redirectToRefererOrHome(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String referer = req.getHeader("referer");
        String contextPath = req.getContextPath();
        String refererUri = referer.substring(
                referer.indexOf(contextPath) + contextPath.length() + 1
        );

        resp.sendRedirect(String.format("%s/%s", contextPath, refererUri));
    }

}

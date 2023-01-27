package com.company.web.filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontControllerPathResolveFilter extends HttpFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FrontControllerPathResolveFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (path.startsWith("/resources/")) {
            chain.doFilter(request, response);
        } else {
            if ("/".equals(path)) {
                LOGGER.debug("redirecting request to home page");
                ((HttpServletResponse)response).sendRedirect("home");
                return;
            }
            String queryString = req.getQueryString();
            request.getRequestDispatcher("/app" + path + "?" + queryString).forward(request, response);
        }
    }

}
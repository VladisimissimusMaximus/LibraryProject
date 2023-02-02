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
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (path.startsWith("/resources/")) {
            chain.doFilter(request, response);
        } else {
            if ("/".equals(path)) {
                LOGGER.debug("redirecting request to home page");
                (response).sendRedirect("home");
                return;
            }
            String queryString = request.getQueryString();
            request.getRequestDispatcher("/app" + path + "?" + queryString).forward(request, response);
        }
    }

}
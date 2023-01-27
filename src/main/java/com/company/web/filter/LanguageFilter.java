package com.company.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LanguageFilter extends HttpFilter {
    private static final String LANGUAGE = "language";
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession(false);

        String language = req.getParameter(LANGUAGE);
        LOGGER.info("received a request {} with language parameter '{}'", req, language);
        if (session != null && language != null) {
            LOGGER.debug("found a valid session {}, trying to resolve language", session);

            session.setAttribute(LANGUAGE, language);
        }

        chain.doFilter(req, resp);
    }

}

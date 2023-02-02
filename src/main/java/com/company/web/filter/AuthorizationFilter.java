package com.company.web.filter;

import com.company.model.UserRole;
import com.company.util.WebUtil;
import com.company.web.Uri;
import com.company.web.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorizationFilter extends HttpFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);
    private List<UserRole> allowedRoles;

    @Override
    public void init() throws ServletException {
        super.init();
        String roleIdInitParam = getFilterConfig().getInitParameter("role-id-list");
        allowedRoles = Arrays.stream(roleIdInitParam.split(","))
                .map(Integer::valueOf)
                .map(UserRole::byId)
                .collect(Collectors.toList());
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        LOGGER.debug("checking {} permissions against a request to {}", allowedRoles, req.getRequestURI());

        Object sessionRoleAttr;
        HttpSession session = req.getSession(false);
        if (session == null || (sessionRoleAttr = session.getAttribute("userRole")) == null) {
            LOGGER.debug("access to {} denied for users without active session", req.getRequestURI());
            resp.sendRedirect(Uri.LOGIN.toAbsolutePath(req.getContextPath()));
            return;
        }

        UserRole sessionRole = UserRole.valueOf(sessionRoleAttr.toString());
        if (allowedRoles.contains(sessionRole)) {
            LOGGER.info("access to {} approved", req.getRequestURI());

            chain.doFilter(req, resp);
        } else {
            LOGGER.warn("access to {} denied for role {}", req.getRequestURI(), sessionRole);
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            req.setAttribute("errorTitle", "error.notAuthorized.title");
            req.setAttribute("errorMessage", "error.notAuthorized.message");
            WebUtil.forward(req, resp, View.ERROR);
        }
    }
}

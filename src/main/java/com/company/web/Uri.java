package com.company.web;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public enum Uri implements Resource {
    LOGOUT("logout"),
    HOME("home"),
    USERS("users"),
    REGISTER("register"),
    LOGIN("login"),
    UPDATE_PROFILE("profile/update"),
    ERROR("error"),
    UPDATE_USER("users/update/%d"),
    DELETE_USER("users/delete/%d"),
    PROFILE("profile"),
    CATALOGUE("catalogue"),
    DELETE_BOOK("catalogue/delete/%d"),
    OPERATIONS("catalogue/operation"),
    READ("catalogue/operation/read/%d"),
    SUBSCRIBE("catalogue/operation/subscribe/%d"),
    APPROVE("catalogue/operation/approve"),
    MY_OPERATIONS("profile/operation"),
    RETURN_BOOK("profile/operation/return/%d"),
    CANCEL_ORDER("profile/operation/cancel/%d"),
    UNSUBSCRIBE("profile/operation/unsub/%d"),
    UPDATE_BOOK("catalogue/update/%d"),
    CREATE_BOOK("catalogue/create");

    private final String path;

    Uri(String uri) {
        this.path = uri;
    }

    @Override
    public String getPath() {
        return path;
    }

    public String toAbsolutePath(String contextPath) {
        return String.format("%s/%s", contextPath, path);
    }

    public static Uri extractUri(HttpServletRequest req) {
        String requestedResource = req.getRequestURI()
                .substring(req.getContextPath().length() + req.getServletPath().length() + 1);
        return Arrays.stream(Uri.values())
                .filter(location -> {
                    String patternString = toPatternString(location.path);
                    return requestedResource.matches(patternString);
                })
                .findAny()
                .orElse(null);
    }

    private static String toPatternString(String value) {
        return String.format("^%s$", value).replaceAll("%d", "\\\\d+");
    }
}
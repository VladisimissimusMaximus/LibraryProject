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
    OPERATIONS("catalogue/operations"),
    READ("catalogue/operations/read/%d"),
    SUBSCRIBE("catalogue/operations/subscribe/%d"),
    APPROVE("catalogue/operations/approve"),
    MY_OPERATIONS("profile/operations"),
    USER_OPERATIONS("users/operations/%d"),
    APPROVE_USER_OPERATION("users/operations/approve"),
    CANCEL_USER_OPERATION("users/operations/%d"),
    RETURN_BOOK("profile/operations/return/%d"),
    CANCEL_ORDER("profile/operations/cancel/%d"),
    UNSUBSCRIBE("profile/operations/unsub/%d"),
    UPDATE_BOOK("catalogue/update/%d"),
    CREATE_BOOK("catalogue/create"),
    ENABLE_USER("users/enable/%d"),
    DISABLE_USER("users/disable/%d");

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
        return extractUri(requestedResource);
    }

    public static Uri extractUri(String stringUri) {
        return Arrays.stream(Uri.values())
                .filter(location -> {
                    String patternString = toPatternString(location.path);
                    return stringUri.matches(patternString);
                })
                .findAny()
                .orElse(null);
    }

    private static String toPatternString(String value) {
        return String.format("^%s$", value).replaceAll("%d", "\\\\d+");
    }
}
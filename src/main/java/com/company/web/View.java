package com.company.web;

public enum View implements Resource{
    HOME("/WEB-INF/jsp/home.jsp"),
    LOGIN("/WEB-INF/jsp/login.jsp"),
    REGISTER("/WEB-INF/jsp/register.jsp"),
    USERS("/WEB-INF/jsp/users.jsp"),
    ERROR("/WEB-INF/jsp/error/error.jsp"),
    UPDATE_USER("/WEB-INF/jsp/updateUser.jsp"),
    PROFILE("/WEB-INF/jsp/profile.jsp"),
    CATALOGUE("/WEB-INF/jsp/catalogue.jsp"),
    SUBSCRIBE("/WEB-INF/jsp/subscribe.jsp"),
    OPERATIONS("/WEB-INF/jsp/operations.jsp"),
    MY_OPERATIONS("/WEB-INF/jsp/myOperations.jsp"),
    UPDATE_BOOK("/WEB-INF/jsp/updateBook.jsp"),
    CREATE_BOOK("/WEB-INF/jsp/createBook.jsp");

    private final String path;

    View(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}

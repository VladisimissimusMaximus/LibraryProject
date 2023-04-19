package com.company.util;

import com.company.dao.BookDAO;
import com.company.dao.OperationDAO;
import com.company.dao.UserDAO;
import com.company.service.BookService;
import com.company.service.OperationService;
import com.company.service.UserService;

public class ApplicationContainer {
    //Book
    private static final BookDAO BOOK_DAO = BookDAO.getInstance();
    private static final BookService BOOK_SERVICE = new BookService(BOOK_DAO);

    //Operation
    private static final OperationDAO OPERATION_DAO = OperationDAO.getInstance();
    private static final OperationService OPERATION_SERVICE = new OperationService(OPERATION_DAO);

    //User
    public static final UserDAO USER_DAO = UserDAO.getInstance();
    public static final UserService USER_SERVICE = new UserService(USER_DAO);

    public static BookService getBookService(){
        return BOOK_SERVICE;
    }

    public static OperationService getOperationService() {
        return OPERATION_SERVICE;
    }

    public static UserService getUserService(){
        return USER_SERVICE;
    }

}

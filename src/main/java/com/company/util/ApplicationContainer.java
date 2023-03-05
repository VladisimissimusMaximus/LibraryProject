package com.company.util;

import com.company.dao.OperationDAO;
import com.company.service.OperationService;

public class ApplicationContainer {
    private static final OperationDAO OPERATION_DAO = OperationDAO.getInstance();
    private static final OperationService OPERATION_SERVICE =
            new OperationService(OPERATION_DAO);

    // it's non static to be able to mock this method
    public OperationService getOperationService() {
        return OPERATION_SERVICE;
    }

    public static ApplicationContainer getContainer() {
        return new ApplicationContainer();
    }

}

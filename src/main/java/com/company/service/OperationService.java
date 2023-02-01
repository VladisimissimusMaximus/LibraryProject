package com.company.service;

import com.company.dao.OperationDAO;
import com.company.model.Book;
import com.company.model.Operation;
import com.company.model.OperationStatus;
import com.company.model.User;
import com.company.util.OperationUtil;
import com.company.util.selection.Filter;
import com.company.util.selection.SelectionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class OperationService {
    private static final Logger logger = LoggerFactory.getLogger(OperationService.class);
    public static final Integer DEFAULT_SUBSCRIPTION_COST_DOLLARS = 10;
    private static final OperationDAO dao = new OperationDAO();

    private static final Integer MINIMUM_DURATION_DAYS = 1;

    public void placeOrder(User user, Book book, String durationParam) {
        logger.info("placing order on book {} for user {}", book.getId(), user.getId());

        OperationUtil.validateDuration(durationParam);
        int duration = Integer.parseInt(durationParam);

        Operation operation = new Operation();

        operation.setBook(book);
        operation.setUser(user);

        operation.setStatus(OperationStatus.ORDER);
        operation.setDuration(duration);

        dao.insertOperation(operation);
    }

    public void approveOrder(User user, Book book) {
        logger.info("approving order on book {} for user {}", book.getId(), user.getId());

        Operation operation = new Operation();

        operation.setBook(book);
        operation.setUser(user);

        operation.setStatus(OperationStatus.SUBSCRIPTION);
        operation.setStartDate(LocalDateTime.now());

        dao.update(operation);
    }

    public void takeToReadingRoom(User user, Book book) {
        logger.info("taking to reading room book {} by user {}", book.getId(), user.getId());

        int duration = MINIMUM_DURATION_DAYS;

        Operation operation = new Operation();

        operation.setBook(book);
        operation.setUser(user);

        operation.setStatus(OperationStatus.READING_ROOM);
        operation.setDuration(duration);

        dao.insertOperation(operation);
    }

    public void returnBook(User user, Book book) {
        logger.info("returning book {} for user {}", book.getId(), user.getId());

        Operation operation = new Operation();

        operation.setBook(book);
        operation.setUser(user);

        dao.delete(operation);
    }

    public List<Operation> getAll(SelectionOptions options) {
        logger.info("looking for all operations");

        return dao.findAll(options);
    }

    public List<Operation> getAllByUser(Integer userId) {
        logger.info("looking for all operations");

        return dao.findByUserId(userId);
    }

    public int getCount(Filter filter) {
        return filter != null
                ? dao.findCount(filter)
                : dao.findCount();
    }

}

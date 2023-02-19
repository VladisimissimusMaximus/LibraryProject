package com.company.service;

import com.company.dao.UserDAO;
import com.company.model.User;
import com.company.util.UserUtil;
import com.company.util.exceptions.DAOException;
import com.company.util.exceptions.DuplicateFieldException;
import com.company.util.exceptions.UserValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public static final UserDAO userDAO = UserDAO.getInstance();

    public List<User> getAllUsers(){
        return userDAO.findAll();
    }

    public void deleteById(Integer id){
        userDAO.delete(id);
    }

    public User getById(Integer id){
        return userDAO.findById(id);
    }

    public User getByEmail(String email){

        return userDAO.findByEmail(email);
    }

    public void register(User user) throws UserValidationException {
        UserUtil.validateEmail(user);
        UserUtil.validateName(user);
        UserUtil.validatePassword(user);

        UserUtil.prepareToSave(user);

        UserUtil.handleDAOException(() -> userDAO.insert(user));
    }

    public void update(User user){
        UserUtil.validateEmail(user);
        UserUtil.validateName(user);

        userDAO.update(user);
    }

    public void updateEnabled(Integer userId, boolean enabled) {
        logger.info("setting 'enabled' to {} for user {}", enabled, userId);
        userDAO.updateEnabled(userId, enabled);
    }

}

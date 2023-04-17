package com.company.service;

import com.company.dao.UserDAO;
import com.company.model.User;
import com.company.util.UserUtil;
import com.company.util.exceptions.UserValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public List<User> getAllUsers(){
        return dao.findAll();
    }

    public void deleteById(Integer id){
        dao.delete(id);
    }

    public User getById(Integer id){
        return dao.findById(id);
    }

    public User getByEmail(String email){

        return dao.findByEmail(email);
    }

    public void register(User user) throws UserValidationException {
        UserUtil.validateEmail(user);
        UserUtil.validateName(user);
        UserUtil.validatePassword(user);

        UserUtil.prepareToSave(user);

        UserUtil.handleDAOException(() -> dao.insert(user));
    }

    public void update(User user){
        UserUtil.validateEmail(user);
        UserUtil.validateName(user);

        dao.update(user);
    }

    public void updateEnabled(Integer userId, boolean enabled) {
        logger.info("setting 'enabled' to {} for user {}", enabled, userId);
        dao.updateEnabled(userId, enabled);
    }

}

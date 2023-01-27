package com.company.service;

import com.company.dao.UserDAO;
import com.company.model.User;
import com.company.util.UserUtil;
import com.company.util.exceptions.UserValidationException;

import java.util.List;

public class UserService {

    public static final UserDAO userDAO = new UserDAO();

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

        userDAO.insert(user);
    }

    public void update(User user){
        UserUtil.validateEmail(user);
        UserUtil.validateName(user);

        userDAO.update(user);
    }

}

package com.company.util;

import com.company.model.User;
import com.company.util.exceptions.UserValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.company.util.ValidationUtil.*;

public class UserUtil {

    public static boolean checkPasswordsEquals(String password, String password1) {
        return Objects.equals(password, password1);
    }

    public static void validateEmail(User user) {
        String email = user.getEmail();

        if (isEmpty(email)) {
            throw UserValidationException.withEmailValidationCode("validation.NotEmpty");
        }
        if (isShorterThan(5, user.getEmail())) {
            throw UserValidationException.withEmailValidationCode("validation.Length.short");
        }
        if (isLongerThan(50, user.getEmail())) {
            throw UserValidationException.withEmailValidationCode("validation.Length.long");
        }
        if (!email.matches("\\S+@\\S+\\.com")){
            throw UserValidationException.withEmailValidationCode("validation.EmailFormat");
        }

    }

    public static void validateName(User user){
        String name = user.getName();
        if (isEmpty(name)) {
            throw UserValidationException.withNameValidationCode("validation.NotEmpty");
        }
        if (isShorterThan(2, user.getName())) {
            throw UserValidationException.withNameValidationCode("validation.Length.short");
        }
        if (isLongerThan(20, user.getName())) {
            throw UserValidationException.withNameValidationCode("validation.Length.long");
        }
    }

    public static void validatePassword(User user){
        String password = user.getPassword();
        if (isEmpty(password)) {
            throw UserValidationException.withPasswordValidationCode("validation.NotEmpty");
        }
        if (isShorterThan(5, user.getPassword())) {
            throw UserValidationException.withPasswordValidationCode("validation.Length.short");
        }
        if (isLongerThan(20, user.getPassword())) {
            throw UserValidationException.withPasswordValidationCode("validation.Length.long");
        }
        if (!password.matches("\\S*\\d\\S*") || !password.matches("\\S*[A-Za-zА-Яа-я]\\S*") ) {
            throw UserValidationException.withPasswordValidationCode("validation.pattern.password");
        }
    }

    public static void renewUserSession(HttpServletRequest req, User user) {
        String language = null;
        HttpSession oldSession = req.getSession(false);
        if (oldSession != null) {
            Object languageAttr = oldSession.getAttribute("language");
            language = languageAttr != null
                    ? languageAttr.toString()
                    : req.getParameter("language");
            oldSession.invalidate();
        }

        HttpSession newSession = req.getSession(true);
        newSession.setAttribute("language", language);
        newSession.setAttribute("userId", user.getId());
        newSession.setAttribute("userRole", user.getRole());
        newSession.setAttribute("userName", user.getName());
        newSession.setAttribute("userEmail", user.getEmail());
    }

}

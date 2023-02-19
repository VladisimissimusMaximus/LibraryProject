package com.company.util;

import com.company.model.User;
import com.company.util.exceptions.DAOException;
import com.company.util.exceptions.DuplicateFieldException;
import com.company.util.exceptions.UserValidationException;
import org.jasypt.util.password.BasicPasswordEncryptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.company.util.ValidationUtil.*;

public class UserUtil {
    private static final BasicPasswordEncryptor ENCRYPTOR = new BasicPasswordEncryptor();
    private static final String NO_ENCRYPTION_PASSWORD_MARK = "${noop}";

    /**
     * Checks whether the given {@code pass} is equal to {@code encryptedPass} param.
     * If {@code encryptedPass} starts with '{noop}' notation passwords are being compared
     * with {@code String.equals()}, otherwise encryptor's comparison is being used
     * @param pass non-encrypted password
     * @param encryptedPass encrypted password
     * @return true if the passwords are equal, otherwise returns false
     */
    public static boolean checkPasswordsEquals(String pass, String encryptedPass) {
        if (pass == null || "".equals(pass)) {
            return false;
        }

        if (encryptedPass.startsWith(NO_ENCRYPTION_PASSWORD_MARK)) {
            return encryptedPass.substring(NO_ENCRYPTION_PASSWORD_MARK.length()).equals(pass);
        } else {
            return ENCRYPTOR.checkPassword(pass, encryptedPass);
        }
    }

    public static void prepareToSave(User user) {
        String encryptedPassword = ENCRYPTOR.encryptPassword((user.getPassword()));

        user.setPassword(encryptedPassword);
        user.setEmail(user.getEmail().toLowerCase());
    }

    public static void handleDAOException(Runnable runnable) {
        try {
            runnable.run();
        } catch (DuplicateFieldException e) {
            throw UserValidationException.withEmailValidationCode("validation.email.duplicate");
        }
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

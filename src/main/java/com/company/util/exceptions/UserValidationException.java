package com.company.util.exceptions;

public class UserValidationException extends RuntimeException {
    private String emailValidation;
    private String nameValidation;
    private String passwordValidation;

    public UserValidationException() {
        emailValidation = nameValidation = passwordValidation = "";
    }

    public String getEmailValidation() {
        return emailValidation;
    }

    public static UserValidationException withEmailValidationCode(String emailValidationCode) {
        UserValidationException exception = new UserValidationException();
        exception.emailValidation = emailValidationCode;
        return exception;
    }

    public String getNameValidation() {
        return nameValidation;
    }

    public static UserValidationException withNameValidationCode(String nameValidationCode) {
        UserValidationException exception = new UserValidationException();
        exception.nameValidation = nameValidationCode;
        return exception;
    }

    public String getPasswordValidation() {
        return passwordValidation;
    }

    public static UserValidationException withPasswordValidationCode(String passwordValidationCode) {
        UserValidationException exception = new UserValidationException();
        exception.passwordValidation = passwordValidationCode;
        return exception;
    }


}

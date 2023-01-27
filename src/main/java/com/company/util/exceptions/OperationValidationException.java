package com.company.util.exceptions;

public class OperationValidationException extends RuntimeException {
    private String durationValidation;

    public static OperationValidationException withDurationValidationCode(String validationCode){
        OperationValidationException e = new OperationValidationException();
        e.durationValidation = validationCode;
        return e;
    }

    public String getDurationValidation() {
        return durationValidation;
    }
}

package com.company.util.exceptions;

public class OperationValidationException extends RuntimeException {
    private String durationValidation;
    private String duplicationCode;

    public static OperationValidationException withDurationValidationCode(String validationCode){
        OperationValidationException e = new OperationValidationException();
        e.durationValidation = validationCode;
        return e;
    }

    public static OperationValidationException withDuplicationValidationCode(String validationCode){
        OperationValidationException e = new OperationValidationException();
        e.duplicationCode = validationCode;
        return e;
    }

    public String getDurationValidation() {
        return durationValidation;
    }

    public String getDuplicationValidation() {
        return duplicationCode;
    }
}

package com.company.util;

import com.company.util.exceptions.OperationValidationException;

import static com.company.util.ValidationUtil.INTEGER_REGEX;

public class OperationUtil {
    private static final Integer MAXIMUM_DURATION_DAYS = 30;

    public static void validateDuration(String duration){

        if (duration == null) {
            throw OperationValidationException.withDurationValidationCode("validation.NotEmpty");
        }
        System.out.println(duration);
        if (!duration.matches(INTEGER_REGEX)) {
            throw OperationValidationException.withDurationValidationCode("validation.number.valid");
        }
        int durationInt = Integer.parseInt(duration);
        if (durationInt < 1) {
            throw OperationValidationException.withDurationValidationCode("validation.number.positive");
        }
        if (durationInt > MAXIMUM_DURATION_DAYS) {
            throw OperationValidationException.withDurationValidationCode("validation.number.large");
        }
    }
}

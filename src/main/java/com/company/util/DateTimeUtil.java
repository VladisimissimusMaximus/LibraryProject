package com.company.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.company.service.OperationService.DEFAULT_SUBSCRIPTION_COST_DOLLARS;
import static java.time.temporal.ChronoUnit.DAYS;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateTimeUtil() {
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static String toString(LocalDate ld) {
        return ld == null ? "" : ld.format(DATE_FORMATTER);
    }

    public static String appendWithDays(LocalDateTime ldt, Integer days) {
        return ldt == null ? "" : ldt.plus(days, DAYS).format(DATE_TIME_FORMATTER);
    }

    public static long calculateDebt(LocalDateTime ldt, Integer days) {
        LocalDateTime endDate = ldt.plus(days, DAYS);
        long daysOverdue = DAYS.between(endDate, LocalDateTime.now());
        long debt = daysOverdue * DEFAULT_SUBSCRIPTION_COST_DOLLARS;
        return debt > 0 ? debt : 0;

    }
}

package com.company.util;

public class ValidationUtil {
    public static final String INTEGER_REGEX = "[-]?\\d+";

    static boolean isEmpty(String string){
        return string == null || "".equals(string.trim());
    }

    static boolean isShorterThan(int length, String string) {
        return string.length() < length;
    }

    static boolean isLongerThan(int length, String string) {
        return string.length() > length;
    }

    public static boolean isNonNullInteger(Object obj) {
        return obj != null && obj.toString().matches(INTEGER_REGEX);
    }
}

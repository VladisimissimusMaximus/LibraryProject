package com.company.util.selection;

import com.company.util.ValidationUtil;

public class Filter {
    private final String column;
    private final Integer value;

    public Filter(String queryString) {
        this.column = extractTargetColumnName(queryString);
        this.value = Integer.valueOf(extractColumnValue(queryString));
    }

    public static boolean isPresent(String queryString) {
        return queryString != null && queryString.contains("filterBy")
                && ValidationUtil.isNonNullInteger(extractColumnValue(queryString));
    }

    public String applyToQuery(String query) {
        String str = String.format("WHERE %s = %d", column, value);
        int insertionIndex;
        if ((insertionIndex = query.indexOf("GROUP BY")) != -1) {
            return String.format("%s%s %s", query.substring(0, insertionIndex), str, query.substring(insertionIndex));
        }
        return String.format("%s %s", query, str);
    }

    public Integer getValue() {
        return value;
    }

    private static String extractTargetColumnName(String string) {
        int startIndex = string.indexOf("filterBy");
        string = string.substring(startIndex);
        int endIndex = string.indexOf("=");
        return string.substring(startIndex, endIndex);
    }

    private static String extractColumnValue(String string) {
        string = string.substring(string.indexOf("filterBy"));
        int startIndex = string.indexOf("=");
        int endIndex;
        if (string.contains("&")) {
            endIndex = string.indexOf("&");
        } else {
            endIndex = string.length();
        }
        return string.substring(startIndex, endIndex);
    }

    @Override
    public String toString() {
        return "Filter{" +
                "column='" + column + '\'' +
                ", value=" + value +
                '}';
    }
}

package com.company.util.selection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Filter {
    private final Map<FilterColumn, String> columnValueMap = new HashMap<>();

    public Filter(String queryString) {
        init(queryString);
    }

    public static boolean isPresent(String queryString) {
        if (queryString == null) return false;

        for (String token : queryString.split("&")) {
            if (token.contains("filterBy") & token.contains("=")) {
                return !"".equals(token.substring(token.indexOf("=") + 1).trim());
            }
        }

        return false;
    }

    public String applyToQuery(String query) {
        for (Map.Entry<FilterColumn, String> entry : columnValueMap.entrySet()) {
            String operator = query.contains("WHERE") ? "AND" : "WHERE";
            query = String.format("%s %s %s = ?", query, operator, entry.getKey().columnName);
        }
        return query;
    }

    private void init(String string) {
        StringTokenizer tokenizer = new StringTokenizer(string, "&");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            FilterColumn column = FilterColumn.toFilterColumn(extractColumnName(token));
            if (column == null) continue;

            int startIndex = token.indexOf("=");
            if (startIndex == -1) continue;

            String value = token.substring(startIndex + 1);
            if ("".equals(value.trim())) continue;

            columnValueMap.put(column, value);
        }
    }

    private static String extractColumnName(String string) {
        int startIndex = string.contains("filterBy") ? "filterBy".length() : 0;
        System.out.println(string);
        return string.substring(startIndex, string.indexOf("="));
    }

    @Override
    public String toString() {
        return "Filter{" +
                "columnValueMap=" + columnValueMap +
                '}';
    }

    public Map<FilterColumn, String> getColumnValueMap() {
        return columnValueMap;
    }

    public enum FilterColumn {
        NAME("books.name"), AUTHOR("books.author"),
        EMAIL("email"), USERID("user_id");
        private final String columnName;

        FilterColumn(String columnName) {
            this.columnName = columnName;
        }

        private static FilterColumn toFilterColumn(String columnName) {
            return Arrays.stream(values())
                    .filter(column -> columnName.equalsIgnoreCase(column.name()))
                    .findFirst()
                    .orElse(null);

        }

        public String getColumnName() {
            return columnName;
        }
    }
}

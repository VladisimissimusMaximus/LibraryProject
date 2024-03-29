package com.company.util.selection;

import java.net.URLDecoder;
import java.nio.charset.Charset;
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

        return Arrays.stream(FilterColumn.values())
                .anyMatch(filterColumn -> queryString.contains(filterColumn.parameterName));
    }

    private void init(String string) {
        StringTokenizer tokenizer = new StringTokenizer(string, "&");

        while (tokenizer.hasMoreTokens()) {
            String token = URLDecoder.decode(tokenizer.nextToken(), Charset.defaultCharset());

            int startIndex = token.indexOf("=");
            if (startIndex == -1) continue;

            String value = token.substring(startIndex + 1);
            if ("".equals(value.trim())) continue;

            FilterColumn column = FilterColumn.toFilterColumn(token);
            if (column == null) continue;

            columnValueMap.put(column, value);
        }
    }

    public String applyToSQLQuery(String query) {
        for (Map.Entry<FilterColumn, String> entry : columnValueMap.entrySet()) {
            String operator = query.contains("WHERE") ? "AND" : "WHERE";
            query = String.format("%s %s %s = ?", query, operator, entry.getKey().columnName);
        }
        return query;
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
        NAME("books.name", "filterByName"),
        AUTHOR("books.author", "filterByAuthor"),
        EMAIL("email", "filterByEmail"),
        USER_ID("user_id", "filterByUserId"),
        OPERATION_STATUS("book_operations.status", "filterByStatus");

        private final String columnName;
        private final String parameterName;

        FilterColumn(String columnName, String parameterName) {
            this.columnName = columnName;
            this.parameterName = parameterName;
        }

        private static FilterColumn toFilterColumn(String keyValueParameterPair) {
            String parameterName = keyValueParameterPair.substring(0, keyValueParameterPair.indexOf("="));
            return Arrays.stream(FilterColumn.values())
                    .filter(filterColumn ->
                            filterColumn.getParameterName()
                                    .equalsIgnoreCase(parameterName))
                    .findAny()
                    .orElse(null);
        }

        public String getParameterName() {
            return parameterName;
        }

        public String getColumnName() {
            return columnName;
        }
    }
}

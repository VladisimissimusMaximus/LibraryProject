package com.company.util.selection;

public enum Order {
    BY_TITLE("books.name"),
    BY_AUTHOR("books.author"),
    BY_PUBLISHER("books.publisher"),
    BY_PUBLICATION_DATE("books.publisher"),
    BY_OPERATION_STATUS("book_operations.status");

    private final String columnName;

    Order(String columnName) {
        this.columnName = columnName;
    }

    public String applyToQuery(String query) {
        return String.format("%s ORDER BY %s", query, columnName);
    }
}

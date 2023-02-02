package com.company.util.selection;

public enum Order {
    BY_TITLE("books.name", "title"),
    BY_AUTHOR("books.author", "author"),
    BY_PUBLISHER("books.publisher", "publisher"),
    BY_PUBLICATION_DATE("books.publication_date", "publicationDate"),
    BY_OPERATION_STATUS("book_operations.status", "status");

    private final String columnName;
    private final String urlAttributeValue;

    Order(String columnName, String urlParameterName) {
        this.columnName = columnName;
        this.urlAttributeValue = urlParameterName;
    }

    public String applyToQuery(String query) {
        return String.format("%s ORDER BY %s", query, columnName);
    }

    public String getAttributeValue() {
        return urlAttributeValue;
    }
}

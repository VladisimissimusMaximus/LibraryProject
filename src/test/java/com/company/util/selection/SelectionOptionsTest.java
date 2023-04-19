package com.company.util.selection;

import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SelectionOptionsTest {

    @Test
    void applyToQuery_whenNoUrlParametersGiven_thenAppendSqlQueryWithDefaultPaging() {
        // given
        String query = "SELECT * FROM books";
        String expectedQuery = new Paging().applyToQuery(query);

        SelectionOptions options = SelectionOptions.build().build();

        // when
        String actualQuery = options.applyToQuery(query);

        // then
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    void applyToQuery_whenFilterParamValueWasNotGiven_thenAppendSqlQueryWithDefaultPagingOnly() {
        // given
        Filter.FilterColumn filterColumn = Filter.FilterColumn.AUTHOR;
        String query = "SELECT * FROM books";
        // default paging is always being applied
        String expectedQuery = new Paging().applyToQuery(query);
        int expectedFiltersCount = 0;

        SelectionOptions options = SelectionOptions.build()
                .withFilter(filterColumn.getParameterName() + "=")
                .build();
        Map<Filter.FilterColumn, String> columnValueMap = options.getFilter().getColumnValueMap();

        // when
        String actualQuery = options.applyToQuery(query);

        // then
        assertEquals(expectedQuery, actualQuery);
        assertEquals(expectedFiltersCount, columnValueMap.size());
    }

    @Test
    void applyToQuery_whenFilterParamGiven_thenAppendSQLTemplateWhereClause() {
        // given
        Filter.FilterColumn filterColumn = Filter.FilterColumn.AUTHOR;
        String expectedValue = "Vasyl";
        int expectedFiltersCount = 1;
        String query = "SELECT * FROM books";
        String expectedQuery = query + " WHERE " + filterColumn.getColumnName() + " = ?";
        // default paging is always being applied
        expectedQuery = new Paging().applyToQuery(expectedQuery);

        String urlQuery = filterColumn.getParameterName() + "=" + expectedValue;
        SelectionOptions options = SelectionOptions.build()
                .withFilter(urlQuery)
                .build();
        Map<Filter.FilterColumn, String> columnValueMap = options.getFilter().getColumnValueMap();

        // when
        String actualQuery = options.applyToQuery(query);

        // then
        assertEquals(expectedQuery, actualQuery);
        assertEquals(expectedFiltersCount, columnValueMap.size());
        assertEquals(expectedValue, columnValueMap.get(filterColumn));
    }

    @Test
    void applyToQuery_whenFilterParamGivenURLEncoded_thenAppendSQLTemplateWithDecodedOne() {
        // given
        Filter.FilterColumn filterColumn = Filter.FilterColumn.AUTHOR;
        String expectedValue = "Vasyl Vasyliovych Vasiuk";
        int expectedFiltersCount = 1;
        String query = "SELECT * FROM books";
        String expectedQuery = query + " WHERE " + filterColumn.getColumnName() + " = ?";
        // default paging is always being applied
        expectedQuery = new Paging().applyToQuery(expectedQuery);

        String urlQuery = filterColumn.getParameterName() + "="
                + URLEncoder.encode(expectedValue, Charset.defaultCharset());
        SelectionOptions options = SelectionOptions.build()
                .withFilter(urlQuery)
                .build();
        Map<Filter.FilterColumn, String> columnValueMap = options.getFilter().getColumnValueMap();

        // when
        String actualQuery = options.applyToQuery(query);

        // then
        assertEquals(expectedQuery, actualQuery);
        assertEquals(expectedFiltersCount, columnValueMap.size());
        assertEquals(expectedValue, columnValueMap.get(filterColumn));
    }

    @Test
    void applyToQuery_whenMultipleFiltersGivenURLEncoded_thenAppendSQLTemplateWithSingleWhereClauseAndMultipleValuesDecoded() {
        // given
        Filter.FilterColumn filterByAuthor = Filter.FilterColumn.AUTHOR;
        String expectedAuthorValue = "Vasyl Vasyliovych Vasiuk";
        Filter.FilterColumn filterByName = Filter.FilterColumn.NAME;
        String expectedNameValue = "Vasyl Vasyliovych Vasiuk";
        int expectedFiltersCount = 2;
        String query = "SELECT * FROM books";

        String urlQuery = filterByAuthor.getParameterName() + "="
                + URLEncoder.encode(expectedAuthorValue, Charset.defaultCharset())
                + "&"
                + filterByName.getParameterName() + "="
                + URLEncoder.encode(expectedNameValue, Charset.defaultCharset());
        SelectionOptions options = SelectionOptions.build()
                .withFilter(urlQuery)
                .build();

        // by default the ordering is determined by the HashMap implementation
        // based on a Filter column (key) hash code - is not being changed during runtime by contract
        // but may vary across multiple executions
        Map<Filter.FilterColumn, String> columnValueMap = options.getFilter().getColumnValueMap();
        List<String> orderedOperands = columnValueMap.keySet().stream()
                .map(Filter.FilterColumn::getColumnName)
                .collect(Collectors.toList());

        String whereClause = " WHERE " +
                String.join(" = ? AND ", orderedOperands) +
                " = ?";
        String expectedQuery = query + whereClause;

        // default paging is always being applied
        expectedQuery = new Paging().applyToQuery(expectedQuery);

        // when
        String actualQuery = options.applyToQuery(query);

        // then
        assertEquals(expectedQuery, actualQuery);
        assertEquals(expectedFiltersCount, columnValueMap.size());
        assertEquals(expectedAuthorValue, columnValueMap.get(filterByAuthor));
        assertEquals(expectedNameValue, columnValueMap.get(filterByName));
    }

    @Test
    void getFilterColumnValues() {
    }

    @Test
    void getFilter() {
    }

    @Test
    void getOrder() {
    }
}
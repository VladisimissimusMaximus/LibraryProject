package com.company.util.selection;

import com.company.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class SelectionOptions {
    private static final Logger logger = LoggerFactory.getLogger(SelectionOptions.class);
    private Order order;
    private Paging paging;
    private Filter filter;

    public static SelectionOptions empty() {
        return new SelectionOptions();
    }

    public String applyToQuery(String query) {
        logger.info("start applying selection options to sql query '{}'", query);
        if (filter != null) {
            query = filter.applyToSQLQuery(query);
        }
        if (order != null) {
            query = order.applyToQuery(query);
        }
        if(paging!=null) {
            query = paging.applyToQuery(query);
        }
        logger.info("finished applying selection options, the result is '{}'", query);
        return query;
    }

    public Paging getPaging() {
        return paging;
    }

    public Map<Filter.FilterColumn, String> getFilterColumnValues() {
        return filter != null ? filter.getColumnValueMap() : Collections.emptyMap();
    }

    public Filter getFilter() {
        return filter;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "SelectionOptions{" +
                "order=" + order +
                ", paging=" + paging +
                ", filter=" + filter +
                '}';
    }

    public static Builder build() {
        return new SelectionOptions().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder withFilter(String queryString) {
            if (Filter.isPresent(queryString)) {
                SelectionOptions.this.filter =
                        new Filter(queryString);
            }
            return this;
        }

        public Builder withOrder(String orderParam) {
            if (orderParam != null) {
                SelectionOptions.this.order = Arrays.stream(Order.values())
                        .filter(orderInstance ->
                                orderInstance.getAttributeValue()
                                        .equalsIgnoreCase(orderParam)
                        )
                        .findAny()
                        .orElse(null);
            }
            return this;
        }

        public Builder withRecordsPerPage(String recordsPerPage) {
            if (ValidationUtil.isNonNullInteger(recordsPerPage)) {
                getPaging().setRecordsPerPage(Integer.parseInt(recordsPerPage));
            }
            return this;
        }

        public Builder withPageNumber(String pageNumber) {
            if (ValidationUtil.isNonNullInteger(pageNumber)) {
                getPaging().setPageNumber(Integer.parseInt(pageNumber));
            }
            return this;
        }

        private Paging getPaging() {
            if (SelectionOptions.this.paging == null) {
                SelectionOptions.this.paging = new Paging();
            }
            return SelectionOptions.this.paging;
        }

        public SelectionOptions build() {
            if (SelectionOptions.this.paging == null) {
                SelectionOptions.this.paging = new Paging();
            }
            return SelectionOptions.this;
        }

    }
}

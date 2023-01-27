package com.company.util.selection;

import com.company.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectionOptions {
    private static final Logger logger = LoggerFactory.getLogger(SelectionOptions.class);
    private Order order;
    private Paging paging;
    private Filter filter;

    public static SelectionOptions empty() {
        return new SelectionOptions();
    }

    public String applyToQuery(String query) {
        logger.info("start applying filter to sql query '{}'", query);
        if (filter != null) {
            query = filter.applyToQuery(query);
        }
        if (order != null) {
            query = order.applyToQuery(query);
        }
        if(paging!=null) {
            query = paging.applyToQuery(query);
        }
        logger.info("finished applying filter, the result is '{}'", query);
        return query;
    }

    public Paging getPaging() {
        return paging;
    }


    public Filter getFilter() {
        return filter;
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
                Order order = null;
                switch (orderParam) {
                    case "title":
                        order = Order.BY_TITLE;
                        break;
                    case "author":
                        order = Order.BY_AUTHOR;
                        break;
                    case "publisher":
                        order = Order.BY_PUBLISHER;
                        break;
                    case "publication_date":
                        order = Order.BY_PUBLICATION_DATE;
                        break;
                }
                SelectionOptions.this.order = order;
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
            return SelectionOptions.this;
        }

    }
}
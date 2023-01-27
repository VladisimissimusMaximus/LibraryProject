package com.company.util.selection;

public class Paging {
    private int recordsPerPage = 10;
    private int offset;

    public String applyToQuery(String query) {
        return String.format("%s LIMIT %d OFFSET %d", query, recordsPerPage, offset);
    }

    public void setPageNumber(Integer pageNumber) {
        offset = recordsPerPage * (pageNumber - 1);
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public int getCurrentPage() {
        return (offset / recordsPerPage) + 1;
    }
}

package org.example.shop.services;

/**
 * Handles pagination
 * @author Matthias Wenning
 * @version 1.7
 * @since 1.7
 */
public class Pagination {
    private int totalNumber;
    private int pageCount;
    private int page;
    private int from;
    private int to;

    public Pagination(int totalParam, int pageSize, int pageParam) {
        totalNumber = totalParam;
        pageCount = (totalParam / pageSize) + 1;

        // catch page > pageCount
        page = Math.min(pageParam, pageCount);
        // catch page < 1
        page = Math.max(page, 1);
        from = (page - 1) * pageSize;
        to = Math.min(from + pageSize, totalNumber);
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPage() {
        return page;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}

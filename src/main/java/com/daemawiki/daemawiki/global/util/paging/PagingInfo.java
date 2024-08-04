package com.daemawiki.daemawiki.global.util.paging;

import com.daemawiki.daemawiki.global.util.sorting.SortByOption;
import com.daemawiki.daemawiki.global.util.sorting.SortDirection;

public record PagingInfo(
        SortByOption sortBy,
        int sortDirection,
        int page,
        int size
) {

    public static PagingInfo fromPagingRequest(PagingRequest request) {
        return new PagingInfo(
                SortByOption.valueOf(request.sort()),
                request.direction(),
                request.page(),
                request.size() + 1
        );
    }

    private PagingInfo(SortByOption sortBy, String sortDirection, int page, int size) {
        this(sortBy, SortDirection.valueOf(sortDirection).getDirectionInt(), page, size);
    }
}
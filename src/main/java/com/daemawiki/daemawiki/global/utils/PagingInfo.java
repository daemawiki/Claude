package com.daemawiki.daemawiki.global.utils;

import org.springframework.data.domain.Sort;

public record PagingInfo(
        String sortBy,
        int sortDirection,
        Integer page,
        Integer size
) {

    public static PagingInfo of(String sortBy, int sortDirection, Integer page, Integer size) {
        return new PagingInfo(sortBy, sortDirection, page, size);
    }

    public PagingInfo(String sortBy, String sortDirection, Integer page, Integer size) {
        this(sortBy, SortDirection.of(sortDirection), page, size);
    }

    enum SortDirection {
        DESC, ASC;
        public static SortDirection of(int sortDirection) {
            return sortDirection == 1 ? ASC : DESC;
        }

        public static int of(String sortDirection) {
            return sortDirection.equalsIgnoreCase(SortDirection.DESC.name()) ? -1 : 1;
        }
    }
}
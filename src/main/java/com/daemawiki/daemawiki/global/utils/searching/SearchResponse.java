package com.daemawiki.daemawiki.global.utils.searching;

import java.util.List;

public record SearchResponse<T>(
        List<T> data,
        int totalData,
        int currentPage,
        boolean hasNextPage
) {
    public static <T> SearchResponse<T> of(List<T> data, int totalData, int currentPage, boolean hasNextPage) {
        return new SearchResponse<>(data, totalData, currentPage, hasNextPage);
    }
}

package com.daemawiki.daemawiki.common.util.sorting;

import lombok.Getter;

@Getter
public enum SortByOption {
    CREATED("dateTime.created"),
    UPDATED("dateTime.updated"),
    TITLE("title"),
    VIEW("viewCount");

    private final String path;

    SortByOption(String path) {
        this.path = path;
    }
}

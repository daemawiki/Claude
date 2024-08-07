package com.daemawiki.daemawiki.global.utils.sorting;

import lombok.Getter;

@Getter
public enum SortByOption {
    CREATED("dateTime.created"),
    UPDATED("dateTime.updated"),
    TITLE("title"),
    VIEW("view");

    private final String path;

    SortByOption(String path) {
        this.path = path;
    }
}

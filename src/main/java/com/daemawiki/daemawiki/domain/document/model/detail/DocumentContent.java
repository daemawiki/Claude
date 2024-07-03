package com.daemawiki.daemawiki.domain.document.model.detail;

public record DocumentContent(
        int index,
        String title,
        String content
) {
    public static DocumentContent of(int index, String title, String content) {
        return new DocumentContent(index, title, content);
    }
}

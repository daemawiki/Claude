package com.daemawiki.daemawiki.domain.document.model.detail;

public record DocumentEditor(
        String name,
        String email,
        String userId
) {
    public static DocumentEditor of(String name, String email, String userId) {
        return new DocumentEditor(name, email, userId);
    }
}

package com.daemawiki.daemawiki.domain.mail.model;

public record AuthCodeModel(
        String email,
        String code
) {
    public static AuthCodeModel of(String email, String code) {
        return new AuthCodeModel(email, code);
    }
}

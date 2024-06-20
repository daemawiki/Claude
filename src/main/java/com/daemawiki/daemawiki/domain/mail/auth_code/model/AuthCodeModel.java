package com.daemawiki.daemawiki.domain.mail.auth_code.model;

public record AuthCodeModel(
        String email,
        String code
) {
    public static AuthCodeModel of(String email, String code) {
        return new AuthCodeModel(email, code);
    }
}

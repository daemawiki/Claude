package com.daemawiki.daemawiki.domain.mail.model;

public record AuthUserModel(
        String email
) {
    public static AuthUserModel of(String email) {
        return new AuthUserModel(email);
    }
}

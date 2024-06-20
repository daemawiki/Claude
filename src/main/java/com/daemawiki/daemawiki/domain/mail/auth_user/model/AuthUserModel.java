package com.daemawiki.daemawiki.domain.mail.auth_user.model;

public record AuthUserModel(
        String email
) {
    public static AuthUserModel of(String email) {
        return new AuthUserModel(email);
    }
}

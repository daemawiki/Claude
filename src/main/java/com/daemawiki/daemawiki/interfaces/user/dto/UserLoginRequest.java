package com.daemawiki.daemawiki.interfaces.user.dto;

public record UserLoginRequest(
        String email,
        String password
) {
}

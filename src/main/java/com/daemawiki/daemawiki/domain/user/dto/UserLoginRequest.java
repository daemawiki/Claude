package com.daemawiki.daemawiki.domain.user.dto;

public record UserLoginRequest(
        String email,
        String password
) {}

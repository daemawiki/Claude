package com.daemawiki.daemawiki.domain.user.dto;

public record UserReissueResponse(
        String token
) {
    public static UserReissueResponse of(String token) {
        return new UserReissueResponse(token);
    }
}

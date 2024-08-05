package com.daemawiki.daemawiki.interfaces.user.dto;

public record UserTokenReissueResponse(
        String token
) {
    public static UserTokenReissueResponse of(String token) {
        return new UserTokenReissueResponse(token);
    }
}

package com.daemawiki.daemawiki.domain.user.dto;

import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record UserLoginResponse(
        String token,
        UserSimpleModel user
) {
    public static UserLoginResponse of(String token, String name, UserRole role) {
        return new UserLoginResponse(
                token,
                new UserSimpleModel(name, role)
        );
    }

    record UserSimpleModel(
            String name,
            UserRole role
    ) {}
}

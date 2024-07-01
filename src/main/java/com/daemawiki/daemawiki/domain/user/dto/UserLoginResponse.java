package com.daemawiki.daemawiki.domain.user.dto;

import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record UserLoginResponse(
        String token,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
        LocalDateTime issuedAt,
        Long expiresIn,
        UserSimpleModel user
) {
    public static UserLoginResponse of(String token, LocalDateTime issuedAt, Long expiresIn, String id, String name, UserRole role) {
        return new UserLoginResponse(
                token,
                issuedAt,
                expiresIn,
                new UserSimpleModel(id, name, role)
        );
    }

    record UserSimpleModel(
            String id,
            String name,
            UserRole role
    ) {}
}

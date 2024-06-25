package com.daemawiki.daemawiki.domain.mail.dto;

import jakarta.validation.constraints.Pattern;

public record AuthCodeRequest(
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(dsm\\.hs\\.kr)$",
                message = "메일 형식이 올바르지 않습니다.")
        String email,

        String type
) {
}
package com.daemawiki.daemawiki.domain.user.model.detail;

public record UserInfo(
        Integer generation,
        String major
) {
    public static UserInfo of(Integer generation, String major) {
        return new UserInfo(generation, major);
    }
}

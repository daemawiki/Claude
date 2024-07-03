package com.daemawiki.daemawiki.domain.document.model.detail;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;

public record DocumentEditor(
        String name,
        String email,
        String userId
) {
    public static DocumentEditor of(String name, String email, String userId) {
        return new DocumentEditor(name, email, userId);
    }

    public static DocumentEditor fromUser(UserEntity user) {
        return new DocumentEditor(user.getName(), user.getEmail(), user.getId());
    }
}

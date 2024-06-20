package com.daemawiki.daemawiki.domain.user.model;

import com.daemawiki.daemawiki.domain.user.model.detail.UserInfo;
import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;

import java.time.LocalDateTime;

public interface UserModel {
    String getName();
    String getEmail();
    String getPassword();
    UserInfo getUserInfo();
    boolean isSuspended();
    LocalDateTime getRegistrationDate();

    UserEntity updateDocumentId(String documentId);
    UserEntity updateUserRole(UserRole role);
    UserEntity updateUserInfo(UserInfo userInfo);
    void changePassword(String password);

    static UserEntity createEntity(String name, String email, String password, String documentId, UserInfo userInfo) {
        return new UserEntity(name, email, password, documentId, userInfo);
    }
}

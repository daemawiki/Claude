package com.daemawiki.daemawiki.domain.user.model;

import com.daemawiki.daemawiki.domain.user.model.detail.UserInfo;
import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
@Getter(value = AccessLevel.PROTECTED)
public class UserEntity implements UserModel {
    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    private String documentId;

    private UserInfo userInfo;

    private boolean isSuspended;

    private LocalDateTime registrationDate;

    private UserRole role;

    protected UserEntity() {}

    protected UserEntity(String name, String email, String password, String documentId, UserInfo userInfo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.documentId = documentId;
        this.userInfo = userInfo;
        this.isSuspended = false;
        this.registrationDate = LocalDateTime.now();
        this.role = UserRole.USER;
    }

    @Override
    public UserEntity updateDocumentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    @Override
    public UserEntity updateUserRole(UserRole role) {
        this.role = role;
        return this;
    }

    @Override
    public UserEntity updateUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    @Override
    public void changePassword(String password) {
        this.password = password;
    }
}

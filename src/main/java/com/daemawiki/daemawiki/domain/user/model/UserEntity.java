package com.daemawiki.daemawiki.domain.user.model;

import com.daemawiki.daemawiki.domain.user.model.detail.ClassInfo;
import com.daemawiki.daemawiki.domain.user.model.detail.UserInfo;
import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "users")
@Getter(value = AccessLevel.PUBLIC)
public class UserEntity {
    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    private String documentId;

    private UserInfo userInfo;

    private List<ClassInfo> classInfos;

    @CreatedDate
    private LocalDate registrationDate;

    private UserRole role;

    public void updateDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public UserEntity updateUserRole(UserRole role) {
        this.role = role;
        return this;
    }

    public UserEntity updateUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public static UserEntity createEntity(String name, String email, String password, String documentId, UserInfo userInfo, List<ClassInfo> classInfos, UserRole role) {
        return new UserEntity(name, email, password, documentId, userInfo, classInfos, role);
    }

    protected UserEntity() {}

    private UserEntity(String name, String email, String password, String documentId, UserInfo userInfo, List<ClassInfo> classInfos, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.documentId = documentId;
        this.userInfo = userInfo;
        this.classInfos = classInfos;
        this.role = role;
    }
}

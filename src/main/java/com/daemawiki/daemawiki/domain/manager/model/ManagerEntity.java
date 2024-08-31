package com.daemawiki.daemawiki.domain.manager.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "managers")
public class ManagerEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String userId;

    public void addUserId(String userId) {
        this.userId = userId;
    }

    protected ManagerEntity() {
    }

    public static ManagerEntity of(String email, String userId) {
        return new ManagerEntity(email, userId);
    }

    private ManagerEntity(String email, String userId) {
        this.email = email;
        this.userId = userId;
    }
}

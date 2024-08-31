package com.daemawiki.daemawiki.security.session.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("session-management")
public class SessionEntity {
    @Id
    private String id;

    private String ip;

    private String token;

    @Indexed(expireAfterSeconds = 60 * 60 * 3)
    private LocalDateTime sessionStartAt;

    protected SessionEntity() {}

    public void refresh() {
        sessionStartAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    private SessionEntity(String address, String token) {
        ip = address;
        this.token = token;
        sessionStartAt = LocalDateTime.now();
    }

    public static SessionEntity of(String address, String token) {
        return new SessionEntity(address, token);
    }
}

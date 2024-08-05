package com.daemawiki.daemawiki.common.security.session.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("session-management")
public record SessionEntity(
        @Id
        String id,
        @Indexed(unique = true)
        String ip,
        String token,
        @Indexed(expireAfterSeconds = 60 * 60 * 3)
        LocalDateTime sessionStartAt
) {
        public static SessionEntity of(String address, String token) {
                return new SessionEntity(null, address, token, LocalDateTime.now());
        }
}

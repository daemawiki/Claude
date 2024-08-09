package com.daemawiki.daemawiki.common.security.session.model;

import org.bson.types.ObjectId;

public record SessionModel(
        String id,
        String ip,
        String userName
) {
    public SessionModel {
        if (id == null) id = ObjectId.get().toHexString();
    }

    public static SessionModel of(String ip, String userName) {
        return of(null, ip, userName);
    }

    public static SessionModel of(String id, String ip, String userName) {
        return new SessionModel(id, ip, userName);
    }
}

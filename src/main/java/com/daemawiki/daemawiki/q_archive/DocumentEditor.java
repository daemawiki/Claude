//package com.daemawiki.daemawiki.domain.document.model.detail;
//
//import com.daemawiki.daemawiki.domain.user.model.UserEntity;
//
//public record DocumentEditor(
//        String name,
//        String email
//) {
//    public static DocumentEditor of(String name, String email) {
//        return new DocumentEditor(name, email);
//    }
//
//    public static DocumentEditor fromUser(UserEntity user) {
//        return of(user.getName(), user.getEmail());
//    }
//}

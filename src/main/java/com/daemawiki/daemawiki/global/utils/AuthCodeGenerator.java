package com.daemawiki.daemawiki.global.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AuthCodeGenerator {
    public String generate(int length) {
        var secureRandom = new SecureRandom();
        var randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes)
                .substring(0, length);
    }
}
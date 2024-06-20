package com.daemawiki.daemawiki.global.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dm.security")
public record SecurityProperties(
        String secret,
        String issuer,
        Integer expiration
) {
}

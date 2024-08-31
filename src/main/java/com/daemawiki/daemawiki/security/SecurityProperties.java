package com.daemawiki.daemawiki.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dm.security")
public record SecurityProperties(
        String secret,
        String issuer,
        Integer expiration,
        String prefix
) {
}

package com.daemawiki.daemawiki.common.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dm.security")
public record SecurityProperties(
        String secret,
        String issuer,
        Integer expiration,
        String prefix
) {
}

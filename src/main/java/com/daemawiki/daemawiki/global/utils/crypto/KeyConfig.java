package com.daemawiki.daemawiki.global.utils.crypto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Configuration
public class KeyConfig {
    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException {
        var keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);

        return keyGen.generateKeyPair();
    }

    @Bean
    public SecretKey sharedSecret() throws NoSuchAlgorithmException {
        var keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        keyGenerator.init(256);

        return keyGenerator.generateKey();
    }
}

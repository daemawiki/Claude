package com.daemawiki.daemawiki.global.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailSenderConfig {
    @Bean
    public JavaMailSender javaMailService() {
        var javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailProperties.host());
        javaMailSender.setUsername(mailProperties.email());
        javaMailSender.setPassword(mailProperties.password());
        javaMailSender.setPort(mailProperties.port());
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding(UTF_8);

        return javaMailSender;
    }

    private Properties getMailProperties() {
        var properties = new Properties();
        properties.put("mail.smtp.socketFactory.port", mailProperties.port());
        properties.put("mail.smtp.auth", mailProperties.auth());
        properties.put("mail.smtp.starttls.enable", mailProperties.starttlsEnable());
        properties.put("mail.smtp.starttls.required", mailProperties.starttlsRequired());
        properties.put("mail.smtp.socketFactory.fallback", mailProperties.socketFactoryFallback());
        properties.put("mail.smtp.socketFactory.class", mailProperties.socketFactoryClass());
        properties.put("mail.smtp.ssl.checkserveridentity", "true");

        return properties;
    }

    private final MailSenderProperties mailProperties;
    private static final String UTF_8 = "UTF-8";
}

package com.daemawiki.daemawiki.application.mail.event.model;

public record MailSendEvent(
        String to,
        String content
) {
}

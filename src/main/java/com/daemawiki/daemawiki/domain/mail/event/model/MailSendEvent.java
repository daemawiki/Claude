package com.daemawiki.daemawiki.domain.mail.event.model;

public record MailSendEvent(
        String to,
        String content
) {
}

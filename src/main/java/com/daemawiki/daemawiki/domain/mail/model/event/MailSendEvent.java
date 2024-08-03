package com.daemawiki.daemawiki.domain.mail.model.event;

public record MailSendEvent(
        String to,
        String content
) {
}

package com.daemawiki.daemawiki.interfaces.mail.controller;

import com.daemawiki.daemawiki.domain.mail.model.type.MailType;
import com.daemawiki.daemawiki.application.mail.usecase.UserMailSendUseCase;
import com.daemawiki.daemawiki.application.mail.usecase.UserMailVerifyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
class MailController {
    @PostMapping("/send")
    Mono<Void> send(
            @RequestParam("target") String target,
            @RequestParam("type") MailType type
    ) {
        return userMailSendUseCase.send(target, type);
    }

    @PostMapping("/verify")
    Mono<Void> verify(
            @RequestParam("target") String target,
            @RequestParam("code") String code
    ) {
        return userMailVerifyUseCase.verify(target, code);
    }

    private final UserMailVerifyUseCase userMailVerifyUseCase;
    private final UserMailSendUseCase userMailSendUseCase;
}

package com.daemawiki.daemawiki.domain.mail.controller;

import com.daemawiki.daemawiki.domain.mail.model.MailType;
import com.daemawiki.daemawiki.domain.mail.usecase.UserMailSendUseCase;
import com.daemawiki.daemawiki.domain.mail.usecase.UserMailVerifyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class MailController {
    @PostMapping("/send")
    public Mono<Void> send(
            @RequestParam("target") String target,
            @RequestParam("type") MailType type
    ) {
        return userMailSendUseCase.send(target, type);
    }

    @PostMapping("/verify")
    public Mono<Void> verify(
            @RequestParam("target") String target,
            @RequestParam("code") String code
    ) {
        return userMailVerifyUseCase.verify(target, code);
    }

    private final UserMailVerifyUseCase userMailVerifyUseCase;
    private final UserMailSendUseCase userMailSendUseCase;
}

package com.daemawiki.daemawiki.domain.mail.controller;

import com.daemawiki.daemawiki.domain.mail.model.MailType;
import com.daemawiki.daemawiki.domain.mail.usecase.UserMailSendUseCase;
import com.daemawiki.daemawiki.domain.mail.usecase.UserMailVerifyUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class MailControllerTest {
    private WebTestClient webTestClient;

    @InjectMocks
    private MailController mailController;

    @Mock
    private UserMailVerifyUseCase userMailVerifyUseCase;
    @Mock
    private UserMailSendUseCase userMailSendUseCase;

    private MultiValueMap<String, String> mailVerifyParams;
    private MultiValueMap<String, String> mailSendParams;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(mailController).build();

        mailVerifyParams = new LinkedMultiValueMap<>();
        mailVerifyParams.add("target", "test@dsm.hs.kr");
        mailVerifyParams.add("code", "123456");

        mailSendParams = new LinkedMultiValueMap<>();
        mailSendParams.add("target", "test@dsm.hs.kr");
        mailSendParams.add("type", MailType.REGISTER.name());
    }

    @Test
    @DisplayName("메일 전송 성공")
    void mail_send_success() {
        when(userMailSendUseCase.send(anyString(), any(MailType.class)))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/mail/send")
                        .queryParams(mailSendParams)
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("메일 전송 실패")
    void mail_send_fails() {
        when(userMailSendUseCase.send(anyString(), any(MailType.class)))
                .thenReturn(Mono.error(new RuntimeException()));

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/mail/send")
                        .queryParams(mailSendParams)
                        .build())
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("메일 전송 실패-without params")
    void mail_send_fails_without_params() {
        when(userMailSendUseCase.send(anyString(), any(MailType.class)))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/mail/send")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    @DisplayName("메일 인증 성공")
    void mail_verify_success() {
        when(userMailVerifyUseCase.verify(anyString(), anyString()))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/mail/verify")
                        .queryParams(mailVerifyParams)
                        .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("메일 인증 실패")
    void mail_verify_fails() {
        when(userMailVerifyUseCase.verify(anyString(), anyString()))
                .thenReturn(Mono.error(new RuntimeException()));

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/mail/verify")
                        .queryParams(mailVerifyParams)
                        .build())
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("메일 인증 실패-without params")
    void mail_verify_fails_without_params() {
        when(userMailVerifyUseCase.verify(anyString(), anyString()))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/mail/verify")
                .exchange()
                .expectStatus().is4xxClientError();
    }
}

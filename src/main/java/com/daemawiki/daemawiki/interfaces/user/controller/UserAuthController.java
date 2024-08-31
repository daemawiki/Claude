package com.daemawiki.daemawiki.interfaces.user.controller;

import com.daemawiki.daemawiki.interfaces.user.dto.UserLoginRequest;
import com.daemawiki.daemawiki.interfaces.user.dto.UserRegisterRequest;
import com.daemawiki.daemawiki.application.user.usecase.UserLoginUseCase;
import com.daemawiki.daemawiki.application.user.usecase.UserRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
class UserAuthController {
    private static final Duration COOKIE_EXPIRATION = Duration.ofHours(3);

    private final UserRegisterUseCase userRegisterUseCase;
    private final UserLoginUseCase userLoginUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> register(@RequestBody UserRegisterRequest request) {
        return userRegisterUseCase.register(request);
    }

    @PostMapping("/login")
    Mono<Void> login(
            @RequestBody UserLoginRequest request,
            ServerHttpResponse serverHttpResponse,
            ServerHttpRequest serverHttpRequest
    ) {
        return userLoginUseCase.login(request, serverHttpRequest)
                .map(sessionValue ->  ResponseCookie.from("sessionId", sessionValue)
                        .path("/api")
                        .httpOnly(true)
                        .secure(true)
                        .maxAge(COOKIE_EXPIRATION)
                        .build())
                .doOnNext(serverHttpResponse::addCookie)
                .then();
    }
}

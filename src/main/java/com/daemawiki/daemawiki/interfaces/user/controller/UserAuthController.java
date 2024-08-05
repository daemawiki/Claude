package com.daemawiki.daemawiki.interfaces.user.controller;

import com.daemawiki.daemawiki.interfaces.user.dto.UserLoginRequest;
import com.daemawiki.daemawiki.interfaces.user.dto.UserRegisterRequest;
import com.daemawiki.daemawiki.interfaces.user.dto.UserTokenReissueResponse;
import com.daemawiki.daemawiki.application.user.usecase.UserLoginUseCase;
import com.daemawiki.daemawiki.application.user.usecase.UserRegisterUseCase;
import com.daemawiki.daemawiki.application.user.usecase.UserTokenReissueUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserAuthController {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> register(@RequestBody UserRegisterRequest request) {
        return userRegisterUseCase.register(request);
    }

    @PostMapping("/login")
    public Mono<Void> login(
            @RequestBody UserLoginRequest request,
            ServerHttpResponse serverHttpResponse,
            ServerHttpRequest serverHttpRequest
    ) {
        return userLoginUseCase.loginSession(request, serverHttpRequest)
                .map(sessionValue ->  ResponseCookie.from("sessionId", sessionValue)
                        .path("/api")
                        .httpOnly(true)
                        .maxAge(60 * 60 * 3L)
                        .build())
                .doOnNext(serverHttpResponse::addCookie)
                .then();
    }

    @PutMapping("/reissue")
    public Mono<UserTokenReissueResponse> reissue(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return userTokenReissueUseCase.reissue(token);
    }

    private final UserRegisterUseCase userRegisterUseCase;
    private final UserTokenReissueUseCase userTokenReissueUseCase;
    private final UserLoginUseCase userLoginUseCase;
}

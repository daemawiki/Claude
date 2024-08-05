package com.daemawiki.daemawiki.application.user.usecase;

import com.daemawiki.daemawiki.interfaces.user.dto.UserLoginRequest;
import com.daemawiki.daemawiki.interfaces.user.dto.UserLoginResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public interface UserLoginUseCase {
    Mono<UserLoginResponse> login(UserLoginRequest request);
    Mono<String> loginSession(UserLoginRequest request, ServerHttpRequest serverHttpRequest);
}

package com.daemawiki.daemawiki.domain.user.usecase;

import com.daemawiki.daemawiki.domain.user.dto.UserLoginRequest;
import com.daemawiki.daemawiki.domain.user.dto.UserLoginResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public interface UserLoginUseCase {
    Mono<UserLoginResponse> login(UserLoginRequest request);
    Mono<String> loginSession(UserLoginRequest request, ServerHttpRequest serverHttpRequest);
}

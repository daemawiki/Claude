package com.daemawiki.daemawiki.application.user.usecase;

import com.daemawiki.daemawiki.interfaces.user.dto.UserLoginRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public interface UserLoginUseCase {
    Mono<String> login(UserLoginRequest request, ServerHttpRequest serverHttpRequest);
}

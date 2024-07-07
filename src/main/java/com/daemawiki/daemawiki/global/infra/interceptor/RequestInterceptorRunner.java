package com.daemawiki.daemawiki.global.infra.interceptor;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface RequestInterceptorRunner {

    Mono<Void> runRequest(ServerWebExchange exchange);

    Mono<Void> runResponse(ServerWebExchange exchange);
}

package com.daemawiki.daemawiki.global.infra.interceptor;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 *
 */
public interface HandlerExchangeInterceptor {

    Mono<Boolean> supports(ServerWebExchange exchange);

    Mono<Boolean> intercept(ServerWebExchange exchange);
}

package com.daemawiki.daemawiki.global.infra.interceptor.response;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface ResponseInterceptorRunner {

    Mono<Void> run(ServerWebExchange exchange);
}

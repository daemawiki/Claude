package com.daemawiki.daemawiki.global.infra.interceptor.request;

import com.daemawiki.daemawiki.global.infra.interceptor.HandlerExchangeInterceptorRunner;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface RequestInterceptorRunner extends HandlerExchangeInterceptorRunner {

    @Override
    Mono<Void> run(ServerWebExchange exchange);
}

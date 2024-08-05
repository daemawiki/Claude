package com.daemawiki.daemawiki.common.infra.interceptor.request;

import com.daemawiki.daemawiki.common.infra.interceptor.HandlerExchangeInterceptorRunner;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface RequestInterceptorRunner extends HandlerExchangeInterceptorRunner {

    @Override
    Mono<Void> run(ServerWebExchange exchange);
}

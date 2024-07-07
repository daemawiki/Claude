package com.daemawiki.daemawiki.global.infra.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class RequestInterceptorRunnerRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    private final RequestInterceptorRunner requestInterceptorRunner;

    @Override
    public Mono<HandlerResult> handle(ServerWebExchange exchange, Object handler) {
        return requestInterceptorRunner.runRequest(exchange)
                .then(super.handle(exchange, handler))
                .doOnNext(it -> requestInterceptorRunner.runResponse(exchange));
    }
}
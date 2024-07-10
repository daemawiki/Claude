package com.daemawiki.daemawiki.global.infra.interceptor;

import com.daemawiki.daemawiki.global.infra.interceptor.request.RequestInterceptorRunner;
import com.daemawiki.daemawiki.global.infra.interceptor.response.ResponseInterceptorRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ExchangeInterceptorRunnerRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    private final RequestInterceptorRunner requestInterceptorRunner;

    private final ResponseInterceptorRunner responseInterceptorRunner;

    @Override
    public Mono<HandlerResult> handle(ServerWebExchange exchange, Object handler) {
        return requestInterceptorRunner.run(exchange)
                .then(super.handle(exchange, handler))
                .flatMap(it -> responseInterceptorRunner.run(exchange)
                        .then(Mono.just(it)));
    }
}
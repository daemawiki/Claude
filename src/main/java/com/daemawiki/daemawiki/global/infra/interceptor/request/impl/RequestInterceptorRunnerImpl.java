package com.daemawiki.daemawiki.global.infra.interceptor.request.impl;

import com.daemawiki.daemawiki.global.infra.interceptor.request.RequestInterceptor;
import com.daemawiki.daemawiki.global.infra.interceptor.request.RequestInterceptorRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RequestInterceptorRunnerImpl implements RequestInterceptorRunner {

    private final ApplicationContext applicationContext;

    private List<RequestInterceptor> requestInterceptors;

    @Override
    public Mono<Void> run(ServerWebExchange exchange) {
        init();

        return Flux.fromIterable(requestInterceptors)
                .filterWhen(it -> it.supports(exchange))
                .flatMap(it -> it.intercept(exchange))
                .then();
    }

    private void init() {
        if (requestInterceptors == null) {
            requestInterceptors = applicationContext.getBeansOfType(RequestInterceptor.class)
                    .values().stream().toList();
        }
    }
}

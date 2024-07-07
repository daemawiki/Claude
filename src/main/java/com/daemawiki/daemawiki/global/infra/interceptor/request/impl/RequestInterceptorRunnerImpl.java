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
        if (requestInterceptors == null) {
            requestInterceptors =
                    applicationContext.getBeansOfType(RequestInterceptor.class).values().stream().toList();
        }

        return Flux.fromIterable(requestInterceptors)
                .map(it -> it.supports(exchange)
                        .filter(isSupport -> isSupport.equals(Boolean.TRUE))
                        .then(it.interceptRequest(exchange)))
                .then();
    }
}

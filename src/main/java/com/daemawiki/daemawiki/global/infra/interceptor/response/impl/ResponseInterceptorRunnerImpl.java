package com.daemawiki.daemawiki.global.infra.interceptor.response.impl;

import com.daemawiki.daemawiki.global.infra.interceptor.response.ResponseInterceptor;
import com.daemawiki.daemawiki.global.infra.interceptor.response.ResponseInterceptorRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResponseInterceptorRunnerImpl implements ResponseInterceptorRunner {

    private final ApplicationContext applicationContext;

    private List<ResponseInterceptor> responseInterceptors;

    @Override
    public Mono<Void> run(ServerWebExchange exchange) {
        init();

        return Flux.fromIterable(responseInterceptors)
                .filterWhen(it -> it.supports(exchange))
                .flatMap(it -> it.interceptResponse(exchange))
                .then();
    }

    private void init() {
        if (responseInterceptors == null) {
            responseInterceptors =
                    applicationContext.getBeansOfType(ResponseInterceptor.class).values().stream().toList();
        }
    }
}

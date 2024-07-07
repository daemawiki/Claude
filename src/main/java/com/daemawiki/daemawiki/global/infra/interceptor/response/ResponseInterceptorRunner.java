package com.daemawiki.daemawiki.global.infra.interceptor.response;

import com.daemawiki.daemawiki.global.infra.interceptor.WebInterceptorRunner;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface ResponseInterceptorRunner extends WebInterceptorRunner {

    @Override
    Mono<Void> run(ServerWebExchange exchange);
}

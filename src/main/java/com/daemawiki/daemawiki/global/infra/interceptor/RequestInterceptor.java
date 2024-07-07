package com.daemawiki.daemawiki.global.infra.interceptor;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * {@link org.springframework.web.reactive.HandlerAdapter HandlerAdapter} 실행 이전 및 이후에 요청을 가로채어 추가적인 처리를 진행
 *
 * @author Daybreak312
 * @since 07-07-2024
 */
public interface RequestInterceptor {

    Mono<Boolean> supports(ServerWebExchange exchange);

    Mono<Boolean> onRequest(ServerWebExchange exchange);

    Mono<Boolean> onResponse(ServerWebExchange exchange);
}

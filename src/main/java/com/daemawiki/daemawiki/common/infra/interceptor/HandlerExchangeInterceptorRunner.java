package com.daemawiki.daemawiki.common.infra.interceptor;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * {@link HandlerExchangeInterceptor}를 묶어 실행하기 위한 실행자
 *
 * @author Daybreak312
 * @since 08-07-2024
 */
public interface HandlerExchangeInterceptorRunner {

    /**
     * <p>{@link HandlerExchangeInterceptor}들 실행</p>
     *
     * <p>{@link HandlerExchangeInterceptor}</p>
     *
     * @param exchange 요청, 응답 객체. Interceptor에서
     */
    Mono<Void> run(ServerWebExchange exchange);
}

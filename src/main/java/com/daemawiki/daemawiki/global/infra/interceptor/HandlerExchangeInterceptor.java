package com.daemawiki.daemawiki.global.infra.interceptor;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Handler로 향하는 요청 객체 혹은 Handler 작업을 끝낸 요청 객체를 도중에 가로채어 추가적인 처리를 진행
 *
 * @author Daybreak312
 * @since 08-07-2024
 */
public interface HandlerExchangeInterceptor {

    /**
     * 해당 요청 혹은 응답에 대한 사용 가능 여부
     *
     * @param exchange 요청, 응답 객체
     * @return 사용 가능 여부
     */
    Mono<Boolean> supports(ServerWebExchange exchange);

    /**
     * 요청, 응답 객체 가공
     *
     * @param exchange 요청, 응답 객체
     * @return 사용 가능 여부
     */
    Mono<Boolean> intercept(ServerWebExchange exchange);
}

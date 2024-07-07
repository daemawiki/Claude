package com.daemawiki.daemawiki.global.infra.interceptor;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>{@link org.springframework.web.reactive.HandlerAdapter HandlerAdapter}({@link org.springframework.web.bind.annotation.RestController RestController}) 실행 전후에 호출되어, {@link ServerWebExchange} 객체에 추가적인 처리를 진행.</p>
 *
 * <p>이후 각 실행 시점에 {@link org.springframework.web.reactive.HandlerAdapter HandlerAdapter}({@link org.springframework.web.bind.annotation.RestController RestController})의 진행 가능 여부와 요청의 성공 여부를 결정</p>
 *
 * @author Daybreak312
 * @since 07-07-2024
 */
public interface RequestInterceptor {

    /**
     * 해당 요청에 대해 사용 가능한 RequestInterceptor 인지의 여부
     *
     * @param exchange 요청, 응답 객체
     * @return 사용 가능 여부
     */
    Mono<Boolean> supports(ServerWebExchange exchange);

    /**
     * {@link org.springframework.web.reactive.HandlerAdapter HandlerAdapter}({@link org.springframework.web.bind.annotation.RestController RestController}) 실행 전 {@link ServerWebExchange} 가공
     *
     * @param exchange 요청, 응답 객체
     * @return 계속해도 실행해도 되는지의 여부
     */
    Mono<Boolean> onRequest(ServerWebExchange exchange);

    /**
     * {@link org.springframework.web.reactive.HandlerAdapter HandlerAdapter}({@link org.springframework.web.bind.annotation.RestController RestController}) 실행 후 {@link ServerWebExchange} 가공
     *
     * @param exchange 요청, 응답 객체
     * @return 해당 요청이 성공적으로 완료되었는지의 여부
     */
    Mono<Boolean> onResponse(ServerWebExchange exchange);
}

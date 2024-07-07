package com.daemawiki.daemawiki.global.infra.interceptor.request;

import com.daemawiki.daemawiki.global.infra.interceptor.WebInterceptor;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>{@link org.springframework.web.reactive.HandlerAdapter HandlerAdapter}({@link org.springframework.web.bind.annotation.RestController RestController}) 실행 전에 호출되어 요청을 가로채 {@link ServerWebExchange} 객체에 추가적인 처리를 진행.</p>
 *
 * <p>이후 {@link org.springframework.web.reactive.HandlerAdapter HandlerAdapter}({@link org.springframework.web.bind.annotation.RestController RestController})으로의 진행 가능 여부 반환</p>
 *
 * @author Daybreak312
 * @since 07-07-2024
 */
public interface RequestInterceptor extends WebInterceptor {

    /**
     * 해당 요청에 대한 사용 가능 여부
     *
     * @param exchange 요청, 응답 객체
     * @return 사용 가능 여부
     */
    Mono<Boolean> supports(ServerWebExchange exchange);

    /**
     * 요청을 가로채어 {@link ServerWebExchange} 가공
     *
     * @param exchange 요청, 응답 객체
     * @return 진행 가능 여부
     */
    Mono<Boolean> intercept(ServerWebExchange exchange);
}

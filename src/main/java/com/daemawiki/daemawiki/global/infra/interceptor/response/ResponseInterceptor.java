package com.daemawiki.daemawiki.global.infra.interceptor.response;

import com.daemawiki.daemawiki.global.infra.interceptor.HandlerExchangeInterceptor;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>{@link org.springframework.web.reactive.HandlerAdapter HandlerAdapter}({@link org.springframework.web.bind.annotation.RestController RestController}) 실행 후에 호출되어 응답을 가로채 {@link ServerWebExchange} 객체에 추가적인 처리를 진행.</p>
 *
 * <p>이후 해당 요청의 성공 여부 반환</p>
 *
 * @author Daybreak312
 * @since 07-07-2024
 */
public interface ResponseInterceptor extends HandlerExchangeInterceptor {

    /**
     * 해당 응답에 대한 사용 가능 여부
     *
     * @param exchange 요청, 응답 객체
     * @return 사용 가능 여부
     */
    @Override
    Mono<Boolean> supports(ServerWebExchange exchange);

    /**
     * {@link org.springframework.web.reactive.HandlerAdapter HandlerAdapter}({@link org.springframework.web.bind.annotation.RestController RestController}) 실행 후 {@link ServerWebExchange} 가공
     *
     * @param exchange 요청, 응답 객체
     * @return 해당 요청의 성공 여부
     */
    @Override
    Mono<Boolean> intercept(ServerWebExchange exchange);
}
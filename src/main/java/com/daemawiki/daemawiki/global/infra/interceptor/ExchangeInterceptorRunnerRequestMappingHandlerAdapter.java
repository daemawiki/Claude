package com.daemawiki.daemawiki.global.infra.interceptor;

import com.daemawiki.daemawiki.global.infra.interceptor.request.RequestInterceptorRunner;
import com.daemawiki.daemawiki.global.infra.interceptor.response.ResponseInterceptorRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * {@link RequestInterceptorRunner}와 {@link ResponseInterceptorRunner}를 실제로 실행하기 위한 {@link org.springframework.web.reactive.HandlerAdapter}
 * <p>
 * {@link org.springframework.web.bind.annotation.RestController}를 통해 설정된 컨트롤러에 대한 {@link org.springframework.web.reactive.HandlerAdapter}인 {@link RequestMappingHandlerAdapter}를 오버라이딩하여 {@link HandlerExchangeInterceptorRunner} 실행을 끼워넣음
 */
@Component
@RequiredArgsConstructor
public class ExchangeInterceptorRunnerRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    private final RequestInterceptorRunner requestInterceptorRunner;

    private final ResponseInterceptorRunner responseInterceptorRunner;

    /**
     * <p>{@link RequestMappingHandlerAdapter}의 {@code handle} 메소드 실행 전 후에 각각 {@link RequestInterceptorRunner}, {@link ResponseInterceptorRunner}를 실행한다.</p>
     *
     * <p>{@code super.handle} 메소드의 실행 이전에 {@link RequestInterceptorRunner}의 완전 실행이 보장되어야 한다.</p>
     *
     * <p>{@code super.handle} 메소드가 완전 실행된 이후 {@link ResponseInterceptorRunner}가 실행되어야 하며, {@code handle}가 종료되기 이전에 {@link ResponseInterceptorRunner}의 완전 실행이 보장되어야 한다.</p>
     */
    @Override
    public Mono<HandlerResult> handle(ServerWebExchange exchange, Object handler) {
        return requestInterceptorRunner.run(exchange)
                .then(super.handle(exchange, handler))
                .flatMap(it -> responseInterceptorRunner.run(exchange)
                        .thenReturn(it));
    }
}
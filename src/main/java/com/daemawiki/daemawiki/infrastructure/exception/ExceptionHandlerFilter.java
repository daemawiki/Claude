package com.daemawiki.daemawiki.infrastructure.exception;

import com.daemawiki.daemawiki.common.error.ErrorResponse;
import com.daemawiki.daemawiki.common.error.exception.CustomException;
import com.daemawiki.daemawiki.common.error.exception.CustomExceptionFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter implements WebFilter {

    private final ObjectMapper mapper;

    @NonNull
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .doOnError(error -> resolveError(error, exchange));

    }

    private void resolveError(Throwable error, ServerWebExchange exchange) {
        try {
            var ex = convertToCustomException(error);
            var errorResponse = resolveCustomException(ex);

            var buffer = exchange.getResponse().bufferFactory().wrap(
                    mapper.writeValueAsBytes(
                            errorResponse
                    )
            );

            // Set Status
            exchange.getResponse().setStatusCode(ex.getStatus());
            // Set Header
            exchange.getResponse().getHeaders().set(
                    HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
            );
            // Set Body
            exchange.getResponse().writeWith(
                    Flux.just(buffer)
            ).block();
        } catch (JsonProcessingException e) {
            throw CustomExceptionFactory.internalServerError(
                    e.getMessage(), e
            );
        }
    }

    private CustomException convertToCustomException(Throwable throwable) {
        if (throwable instanceof CustomException) {
            return (CustomException) throwable;
        } else {
            return CustomExceptionFactory.internalServerError(
                    throwable.getMessage(), throwable.getCause()
            );
        }

    }

    private ErrorResponse resolveCustomException(CustomException exception) {
        return ErrorResponse.ofCustomException(exception);
    }
}

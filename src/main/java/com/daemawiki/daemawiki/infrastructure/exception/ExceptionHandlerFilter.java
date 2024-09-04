package com.daemawiki.daemawiki.infrastructure.exception;

import com.daemawiki.daemawiki.common.error.ErrorResponse;
import com.daemawiki.daemawiki.common.error.exception.CustomException;
import com.daemawiki.daemawiki.common.error.exception.CustomExceptionFactory;
import com.daemawiki.daemawiki.infrastructure.exchange.response.ResponseWriterFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter implements WebFilter {

    private final ResponseWriterFactory responseWriterFactory;

    private final CustomExceptionLogger logger = CustomExceptionLogger.of(this.getClass());

    @NonNull
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .doOnError(error -> resolveError(error, exchange));
    }

    private void resolveError(Throwable error, ServerWebExchange exchange) {
        var ex = convertToCustomException(error);
        var errorResponse = convertExceptionToResponse(ex);

        logger.log(ex, errorResponse, exchange);

        responseWriterFactory.getWriter(exchange)
                .setStatus(ex.getStatus())
                .setBody(errorResponse)
                .end();
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

    private ErrorResponse convertExceptionToResponse(CustomException exception) {
        return ErrorResponse.ofCustomException(exception);
    }
}

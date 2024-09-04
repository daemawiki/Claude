package com.daemawiki.daemawiki.infrastructure.exception;

import com.daemawiki.daemawiki.common.error.ErrorResponse;
import com.daemawiki.daemawiki.common.error.exception.CustomException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomExceptionLogger {

    private final Logger logger;

    public void log(CustomException ex, ErrorResponse er, ServerWebExchange ec) {
        logger.warn(
                "[ {} ] {} \"{}\" - {} {}",
                er.errorId(),
                ex.getStatus().value(),
                ex.getMessage(),
                ec.getRequest().getMethod(),
                ec.getRequest().getPath()
        );
    }

    public static CustomExceptionLogger of(Class<?> c) {
        return new CustomExceptionLogger(
                LoggerFactory.getLogger(c)
        );
    }
}

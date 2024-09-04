package com.daemawiki.daemawiki.infrastructure.exchange.response.impl;

import com.daemawiki.daemawiki.infrastructure.exchange.response.ResponseWriter;
import com.daemawiki.daemawiki.infrastructure.exchange.response.ResponseWriterFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
@RequiredArgsConstructor
public class ResponseWriterFactoryImpl implements ResponseWriterFactory {

    private final ObjectMapper objectMapper;

    @Override
    public ResponseWriter getWriter(ServerWebExchange exchange) {
        return new DefaultResponseWriter(objectMapper, exchange);
    }
}

package com.daemawiki.daemawiki.infrastructure.exchange.response.impl;

import com.daemawiki.daemawiki.infrastructure.exchange.response.ResponseWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
class DefaultResponseWriter implements ResponseWriter {

    private final ObjectMapper objectMapper;

    private final ServerWebExchange exchange;

    @Override
    public ResponseWriter setStatus(int status) {
        return setStatus(HttpStatus.valueOf(status));
    }

    @Override
    public ResponseWriter setStatus(HttpStatus status) {
        exchange.getResponse().setStatusCode(status);

        return this;
    }

    @Override
    public ResponseWriter setHeader(String name, String value) {
        exchange.getResponse().getHeaders().set(name, value);

        return this;
    }

    @SneakyThrows
    @Override
    public ResponseWriter setBody(Object body) {

        exchange.getResponse().getHeaders().set(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
        );

        var buffer = exchange.getResponse().bufferFactory().wrap(
                objectMapper.writeValueAsBytes(
                        body
                )
        );

        exchange.getResponse().writeWith(
                Flux.just(buffer)
        );

        return this;
    }

    @Override
    public ServerWebExchange end() {
        return exchange;
    }
}

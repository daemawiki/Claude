package com.daemawiki.daemawiki.infrastructure.exchange.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;

public interface ResponseWriter {
    
    ResponseWriter setStatus(int status);
    
    ResponseWriter setStatus(HttpStatus status);
    
    ResponseWriter setHeader(String name, String value);
    
    ResponseWriter setBody(Object body);

    ServerWebExchange end();
}

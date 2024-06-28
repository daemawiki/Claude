package com.daemawiki.daemawiki.global.error.customs;

import com.daemawiki.daemawiki.global.error.Error;
import com.daemawiki.daemawiki.global.error.exception.CustomException;

public class WrongRedisConnectionException extends CustomException {
    public static final CustomException EXCEPTION = new WrongRedisConnectionException();

    private WrongRedisConnectionException() {
        super(Error.REDIS_CONNECT_FAILED);
    }
}

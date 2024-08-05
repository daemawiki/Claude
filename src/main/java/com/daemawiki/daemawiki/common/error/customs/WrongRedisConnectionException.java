package com.daemawiki.daemawiki.common.error.customs;

import com.daemawiki.daemawiki.common.error.Error;
import com.daemawiki.daemawiki.common.error.exception.CustomException;

public class WrongRedisConnectionException extends CustomException {
    public static final CustomException EXCEPTION = new WrongRedisConnectionException();

    private WrongRedisConnectionException() {
        super(Error.REDIS_CONNECT_FAILED);
    }
}

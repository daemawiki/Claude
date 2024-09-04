package com.daemawiki.daemawiki.common.util.http;

import java.util.List;

public record ListRequest<T>(
        List<T> list
) {
}

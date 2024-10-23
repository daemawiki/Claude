package com.daemawiki.daemawiki.application.document.util;

import reactor.core.publisher.Mono;

import java.util.List;

public interface FlowContext {

    Mono<Flow> getFlow(final String documentId);

    interface Flow extends AutoCloseable {

        List<Element> getElements();

        void createElement(String content);

        void createElement(int lastElementId, String content);
    }

    interface Element {

        int id();

        String content();
    }
}

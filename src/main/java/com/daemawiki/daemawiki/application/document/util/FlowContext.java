package com.daemawiki.daemawiki.application.document.util;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface FlowContext {

    Mono<Optional<Flow>> getFlow(final String documentId);

    void closeFlow(final String documentId);

    interface Flow {

        List<Element> getElements();

        void createElement(String content);

        void createElement(int lastElementId, String content);
    }

    interface Element {

        int id();

        String content();
    }
}

package com.daemawiki.daemawiki.application.document.util;

import com.daemawiki.daemawiki.common.error.exception.CustomExceptionFactory;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class FlowContextImpl implements FlowContext {

    private final DocumentRepository documentRepository;

    private final HashMap<String, Flow> flows = new HashMap<>();

    @SuppressWarnings("resource")
    @Override
    public Mono<Flow> getFlow(final String documentId) {
        return Mono.just(this.flows.get(documentId))
                .switchIfEmpty(Mono.defer(() ->
                        documentRepository.findById(documentId)
                                .switchIfEmpty(Mono.error(() -> CustomExceptionFactory.notFound("document not found.")))
                                .map(it -> new FlowImpl(documentId, this)
                                        .withCreateElements(it.content().lines()))
                ));
    }

    @RequiredArgsConstructor
    public static class FlowImpl implements Flow {

        private final String documentId;

        private final FlowContextImpl flowContext;

        private int elementIdCounter = 0;

        private boolean isClosed = false;

        @Getter
        private final List<ElementImpl> elements = Collections.synchronizedList(new ArrayList<>());

        @Override
        synchronized public void createElement(final String content) {
            verifyNotClosed();

            this.elements.add(elementOf(content));
        }

        @Override
        synchronized public void createElement(final int lastElementId, final String content) {
            verifyNotClosed();

            this.elements.add(
                    this.elements.indexOf(
                            this.elements.stream().filter(it -> it.id == lastElementId).findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Element not found"))
                    ),
                    elementOf(content)
            );
        }

        synchronized public Flow withCreateElements(final Stream<String> contents) {
            verifyNotClosed();

            contents.forEach(this::createElement);
            return this;
        }

        @Override
        public void close() {
            this.flowContext.flows.remove(this.documentId);
            this.isClosed = true;
        }

        @Override
        public String toString() {
            verifyNotClosed();

            var string = new StringBuilder();
            this.elements.forEach(it -> string.append(it.content()));
            return string.toString();
        }

        private ElementImpl elementOf(final String content) {
            verifyNotClosed();

            return new ElementImpl(this.elementIdCounter++, content);
        }

        private void verifyNotClosed() {
            if (this.isClosed) {
                throw new IllegalStateException("Flow is closed");
            }
        }
    }

    record ElementImpl(int id, String content) implements Element {
    }
}

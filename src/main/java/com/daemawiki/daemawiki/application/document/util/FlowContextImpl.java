package com.daemawiki.daemawiki.application.document.util;

import com.daemawiki.daemawiki.common.error.exception.CustomExceptionFactory;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class FlowContextImpl implements FlowContext {

    private final DocumentRepository documentRepository;

    private final HashMap<String, Flow> flows = new HashMap();

    @Override
    public Mono<Optional<Flow>> getFlow(final String documentId) {
        return Mono.just(this.flows.get(documentId))
                .switchIfEmpty(
                        documentRepository.findById(documentId)
                                .switchIfEmpty(Mono.error(CustomExceptionFactory.notFound("document not found.")))
                                .map(it -> new FlowImpl(documentId)
                                        .withCreateElements(it.content().lines()))
                );
    }

    @Override
    public void closeFlow(String documentId) {
        this.flows.remove(documentId);
    }

    @Getter
    public static class FlowImpl implements Flow {

        public FlowImpl(String documentId) {
            this.documentId = documentId;
        }

        private final String documentId;

        private int elementIdCounter = 0;

        @Getter
        private final List<ElementImpl> elements = Collections.synchronizedList(new ArrayList<>());

        @Override
        synchronized public void createElement(final String content) {
            this.elements.add(elementOf(content));
        }

        @Override
        synchronized public void createElement(final int lastElementId, final String content) {
            this.elements.add(
                    this.elements.indexOf(
                            this.elements.stream().filter(it -> it.id == lastElementId).findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Element not found"))
                    ),
                    elementOf(content)
            );
        }

        synchronized public Flow withCreateElements(final Stream<String> contents) {
            contents.forEach(this::createElement);
            return this;
        }

        private ElementImpl elementOf(final String content) {
            return new ElementImpl(elementIdCounter++, content);
        }

        @Override
        public String toString() {
            var string = new StringBuilder();
            this.elements.forEach(it -> string.append(it.content()));
            return string.toString();
        }
    }

    record ElementImpl(int id, String content) implements Element {
    }
}

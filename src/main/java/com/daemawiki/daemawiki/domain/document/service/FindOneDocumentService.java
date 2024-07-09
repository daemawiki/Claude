package com.daemawiki.daemawiki.domain.document.service;

import com.daemawiki.daemawiki.domain.document.dto.FullDocumentResponse;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.domain.document.usecase.FindOneDocumentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FindOneDocumentService implements FindOneDocumentUseCase {

    @Override
    public Mono<FullDocumentResponse> findById(String documentId) {
        return documentRepository.findById(documentId)
                .switchIfEmpty(Mono.error(new RuntimeException())) // 문서 id로 찾지 못했을 때
                .map(FullDocumentResponse::fromDocumentEntity);
    }

    private final DocumentRepository documentRepository;
}

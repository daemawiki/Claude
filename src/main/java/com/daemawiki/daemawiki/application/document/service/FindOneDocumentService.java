package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.FindOneDocumentUseCase;
import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class FindOneDocumentService implements FindOneDocumentUseCase {

    @Override
    public Mono<FullDocumentResponse> findById(String documentId) {
        return documentRepository.findById(documentId)
                .switchIfEmpty(Mono.error(new RuntimeException())) // 문서 id로 찾지 못했을 때
                .map(FullDocumentResponse::fromEntity);
    }

    private final DocumentRepository documentRepository;
}

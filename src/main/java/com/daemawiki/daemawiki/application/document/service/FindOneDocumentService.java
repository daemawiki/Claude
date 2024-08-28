package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.FindOneDocumentUseCase;
import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class FindOneDocumentService implements FindOneDocumentUseCase {

    @Override
    @Transactional(readOnly = true)
    public Mono<FullDocumentResponse> findById(String documentId) {
        return documentRepository.findById(documentId)
                .switchIfEmpty(Mono.error(new RuntimeException())) // 문서 id로 찾지 못했을 때
                .map(FullDocumentResponse::createNewInstanceFromDocumentModel);
    }

    private final DocumentRepository documentRepository;
}

package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.GetRandomDocumentUseCase;
import com.daemawiki.daemawiki.domain.document.DocumentEntityMapper;
import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class GetRandomDocumentService implements GetRandomDocumentUseCase {

    @Override
    @Transactional(readOnly = true)
    public Mono<FullDocumentResponse> get() {
        return documentRepository.getRandom()
                .map(DocumentEntityMapper::toFullResponse);
    }

    private final DocumentRepository documentRepository;
}

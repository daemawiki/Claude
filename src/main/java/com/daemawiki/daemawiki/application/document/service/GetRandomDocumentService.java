package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.GetRandomDocumentUseCase;
import com.daemawiki.daemawiki.domain.document.model.DocumentEntityMapper;
import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class GetRandomDocumentService implements GetRandomDocumentUseCase {

    @Override
    public Mono<FullDocumentResponse> get() {
        return documentRepository.getRandom()
                .map(DocumentEntityMapper::toFullResponse);
    }

    private final DocumentRepository documentRepository;
}

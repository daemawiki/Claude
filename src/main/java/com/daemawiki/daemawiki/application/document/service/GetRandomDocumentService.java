package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.GetRandomDocumentUseCase;
import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetRandomDocumentService implements GetRandomDocumentUseCase {

    @Override
    public Mono<FullDocumentResponse> get() {
        return documentRepository.getRandom()
                .map(FullDocumentResponse::fromDocumentEntity);
    }

    private final DocumentRepository documentRepository;
}

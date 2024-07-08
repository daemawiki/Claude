package com.daemawiki.daemawiki.domain.document.service;

import com.daemawiki.daemawiki.domain.document.dto.FullDocumentResponse;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.domain.document.usecase.GetRandomDocumentUseCase;
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

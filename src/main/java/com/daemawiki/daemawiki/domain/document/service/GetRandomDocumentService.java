package com.daemawiki.daemawiki.domain.document.service;

import com.daemawiki.daemawiki.domain.document.dto.RandomDocumentResponse;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.domain.document.usecase.GetRandomDocumentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetRandomDocumentService implements GetRandomDocumentUseCase {

    @Override
    public Mono<RandomDocumentResponse> get() {
        return documentRepository.getRandom()
                .map(RandomDocumentResponse::of);
    }

    private final DocumentRepository documentRepository;
}

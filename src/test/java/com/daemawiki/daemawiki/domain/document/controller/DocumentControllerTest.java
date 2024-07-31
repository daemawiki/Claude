package com.daemawiki.daemawiki.domain.document.controller;

import com.daemawiki.daemawiki.domain.document.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;

public class DocumentControllerTest {
    @InjectMocks
    private DocumentController documentController;
    @Mock
    private UpdateDocumentContentsUseCase updateDocumentContentsUseCase;
    @Mock
    private UpdateDocumentInfoUseCase updateDocumentInfoUseCase;
    @Mock
    private GetRandomDocumentUseCase getRandomDocumentUseCase;
    @Mock
    private FindOneDocumentUseCase findOneDocumentUseCase;
    @Mock
    private SearchDocumentUseCase searchDocumentUseCase;
    @Mock
    private DeleteDocumentUseCase deleteDocumentUseCase;
    @Mock
    private CreateDocumentUseCase createDocumentUseCase;

    private WebTestClient webTestClient;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(documentController).build();
    }

    void test() {
    }
}

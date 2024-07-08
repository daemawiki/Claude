package com.daemawiki.daemawiki.domain.document.dto;

public record RandomDocumentResponse(
        String documentId
) {
    public static RandomDocumentResponse of(String documentId) {
        return new RandomDocumentResponse(documentId);
    }
}

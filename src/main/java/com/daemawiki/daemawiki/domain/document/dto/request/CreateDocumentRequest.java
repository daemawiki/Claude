package com.daemawiki.daemawiki.domain.document.dto.request;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentType;

import java.util.List;

public record CreateDocumentRequest(
        String title,
        DocumentType type,
        List<String> category
) {
    public static CreateDocumentRequest of(String title, DocumentType type, List<String> category) {
        return new CreateDocumentRequest(title, type, category);
    }
}

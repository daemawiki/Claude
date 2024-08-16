package com.daemawiki.daemawiki.interfaces.document.dto.request;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;

import java.util.Set;

public record CreateDocumentRequest(
        String title,
        String type,
        Set<String> category
) {
}

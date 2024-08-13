package com.daemawiki.daemawiki.interfaces.document.dto.request;

import java.util.List;

public record CreateDocumentRequest(
        String title,
        String type,
        List<String> category
) {
}

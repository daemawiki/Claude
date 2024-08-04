package com.daemawiki.daemawiki.domain.document.model.update.detail;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentEditor;

import java.util.List;

public record DocumentEditorUpdateHistory(
        List<DocumentEditor> removed,
        List<DocumentEditor> added
) {
}

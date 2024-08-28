package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;

import java.util.List;
import java.util.Set;

public class DefaultDocumentModelFactory {

    private static final String GENERATION_TITLE = "기수";
    private static final String GENERATION_SUFFIX = "기";
    private static final String MAJOR_TITLE = "전공";

    public static DocumentModel createDocumentModelByStudentEntity(UserEntity user) {
        final var generation = user.getUserInfo().generation() + GENERATION_SUFFIX;
        final var major = user.getUserInfo().major();

        var details = List.of(
                new DocumentElementDtos.Detail(GENERATION_TITLE, generation),
                new DocumentElementDtos.Detail(MAJOR_TITLE, major)
        );
        final var title = user.getName();

        return createDocumentEntity(
                new DocumentElementDtos.Title(title, title),
                details,
                Set.of(generation, major),
                DocumentElementDtos.Type.STUDENT,
                new DocumentElementDtos.Editor(user.getName(), user.getId())
        );
    }

    public static DocumentModel createDocumentEntity(DocumentElementDtos.Title title, List<DocumentElementDtos.Detail> details, Set<String> category, DocumentElementDtos.Type type, DocumentElementDtos.Editor owner) {
        return new DocumentModel(
                title,
                details,
                category,
                type,
                owner
        );
    }
}

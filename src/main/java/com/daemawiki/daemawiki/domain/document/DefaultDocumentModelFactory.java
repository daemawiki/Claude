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
                new DocumentElementDtos.DetailDto(GENERATION_TITLE, generation),
                new DocumentElementDtos.DetailDto(MAJOR_TITLE, major)
        );
        final var title = user.getName();

        return createDocumentEntity(
                new DocumentElementDtos.TitleDto(title, title),
                details,
                Set.of(generation, major),
                DocumentElementDtos.TypeDto.STUDENT,
                new DocumentElementDtos.EditorDto(user.getName(), user.getId())
        );
    }

    public static DocumentModel createDocumentEntity(DocumentElementDtos.TitleDto title, List<DocumentElementDtos.DetailDto> details, Set<String> category, DocumentElementDtos.TypeDto type, DocumentElementDtos.EditorDto owner) {
        return new DocumentModel(
                title,
                details,
                category,
                type,
                owner
        );
    }
}

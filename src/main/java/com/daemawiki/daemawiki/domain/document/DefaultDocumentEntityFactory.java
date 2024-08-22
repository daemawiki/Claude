package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.interfaces.document.dto.request.CreateDocumentRequest;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;

import java.util.List;
import java.util.Set;

public class DefaultDocumentEntityFactory {

    private static final String GENERATION_TITLE = "기수";
    private static final String GENERATION_SUFFIX = "기";
    private static final String MAJOR_TITLE = "전공";

    public static DocumentEntity createStudentDocumentEntity(UserEntity user) {
        var generation = user.getUserInfo().generation() + GENERATION_SUFFIX;
        var major = user.getUserInfo().major();

        var details = List.of(
                new DocumentEntity.Detail(GENERATION_TITLE, generation),
                new DocumentEntity.Detail(MAJOR_TITLE, major)
        );

        return createDocumentEntity(
                new CreateDocumentRequest(
                        user.getName(),
                        DocumentEntity.Type.STUDENT.name(),
                        Set.of(generation, major)
                ),
                details,
                DocumentEntity.Editor.fromUser(user)
        );
    }

    public static DocumentModel createDocumentEntity(CreateDocumentRequest request, List<DocumentEntity.Detail> details, DocumentEntity.Editor owner) {
        return DocumentEntity.createEntity(
                new DocumentEntity.Title(
                        request.title(),
                        request.title()
                ),
                details,
                request.category(),
                DocumentEntity.Type.valueOf(request.type()),
                owner
        );
    }
}
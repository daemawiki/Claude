package com.daemawiki.daemawiki.domain.document.model;

import com.daemawiki.daemawiki.domain.document.dto.CreateDocumentRequest;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentEditor;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfoVO;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfoDetail;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentType;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;

import java.util.Collections;
import java.util.List;

public class DefaultDocumentEntityFactory {

    private static final String GENERATION_TITLE = "기수";
    private static final String GENERATION_SUFFIX = "기";
    private static final String MAJOR_TITLE = "전공";

    public static DocumentEntity createStudentDocumentEntity(UserEntity user) {
        var generation = user.getUserInfo().generation() + GENERATION_SUFFIX;
        var major = user.getUserInfo().major();

        var details = List.of(
                DocumentInfoDetail.of(GENERATION_TITLE, generation),
                DocumentInfoDetail.of(MAJOR_TITLE, major)
        );

        return createDocumentEntity(
                CreateDocumentRequest.of(
                        user.getName(),
                        DocumentType.STUDENT,
                        List.of(generation, major)
                ),
                details,
                DocumentEditor.fromUser(user)
        );
    }

    public static DocumentEntity createDocumentEntity(CreateDocumentRequest request, List<DocumentInfoDetail> details, DocumentEditor owner) {
        return DocumentEntity.createEntity(
                request.title(),
                DocumentInfoVO.of(
                        request.title(),
                        details == null ? Collections.emptyList() : details
                ),
                request.category(),
                request.type(),
                owner
        );
    }
}

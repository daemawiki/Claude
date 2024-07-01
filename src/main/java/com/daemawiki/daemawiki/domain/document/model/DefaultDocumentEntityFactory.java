package com.daemawiki.daemawiki.domain.document.model;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfo;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfoDetail;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentType;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;

import java.util.List;

public class DefaultDocumentEntityFactory {

    private static final String GENERATION_TITLE = "기수";
    private static final String GENERATION_SUFFIX = "기";
    private static final String MAJOR_TITLE = "전공";

    public static DocumentEntity createStudentDocument(UserEntity user) {
        return DocumentEntity.createEntity(
                user.getName(),
                DocumentInfo.of(
                        user.getName(),
                        List.of(
                                DocumentInfoDetail.of(
                                        GENERATION_TITLE,
                                        user.getUserInfo().generation() + GENERATION_SUFFIX
                                ),
                                DocumentInfoDetail.of(
                                        MAJOR_TITLE,
                                        user.getUserInfo().major()
                                )
                        )
                ),
                DocumentType.STUDENT
        );
    }
}

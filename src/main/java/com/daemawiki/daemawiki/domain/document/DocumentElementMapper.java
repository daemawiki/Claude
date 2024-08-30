package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

/**
 * layer간의 dto 클래스가 Document의 요소 클래스로 변환 과정이 필요할 때 해당 Mapper 클래스를 사용합니다.
 * */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentElementMapper {

    /* to Document elements */
    DocumentEntity.Title toTitle(DocumentElementDtos.Title dto);

    DocumentEntity.Detail toDetail(DocumentElementDtos.Detail dto);

    DocumentEntity.Editor toEditor(DocumentElementDtos.Editor dto);

    DocumentEntity.EditDateTime toEditDateTime(DocumentElementDtos.EditDateTime dto);

    default Tuple2<List<DocumentEntity.Detail>, DocumentEntity.Title> toInfoTuple(DocumentElementDtos.UpdateInfo dto) {
        return Tuples.of(
                toDetailList(dto.detailList()),
                toTitle(dto.title())
        );
    }

    static DocumentEntity.Editor fromUserToEditor(UserEntity user) {
        return DocumentEntity.Editor.fromUser(user);
    }

    DocumentEntity.Content toContent(DocumentElementDtos.Content dto);

    DocumentEntity.Type toType(DocumentElementDtos.Type typeDto);

    default List<DocumentEntity.Content> toContentList(List<DocumentElementDtos.Content> dtoList) {
        return dtoList.stream()
                .map(this::toContent)
                .toList();
    }

    default List<DocumentEntity.Editor> toEditorList(List<DocumentElementDtos.Editor> dtoList) {
        return dtoList.stream()
                .map(this::toEditor)
                .toList();
    }

    default List<DocumentEntity.Detail> toDetailList(List<DocumentElementDtos.Detail> dtoList) {
        return dtoList.stream()
                .map(this::toDetail)
                .toList();
    }

    /* to dtos */
    DocumentElementDtos.Title toTitleDto(DocumentEntity.Title documentTitle);

    DocumentElementDtos.Detail toDetailDto(DocumentEntity.Detail documentDetail);

    DocumentElementDtos.Editor toEditorDto(DocumentEntity.Editor documentEditor);

    static DocumentElementDtos.Editor fromUserToEditorDto(UserEntity user) {
        return new DocumentElementDtos.Editor(user.getName(), user.getId());
    }

    DocumentElementDtos.EditDateTime toEditDateTimeDto(DocumentEntity.EditDateTime documentEditDateTime);

    DocumentElementDtos.Content toContentDto(DocumentEntity.Content documentContent);

    DocumentElementDtos.Type toTypeDto(DocumentEntity.Type documentType);

    default List<DocumentElementDtos.Content> toContentDtoList(List<DocumentEntity.Content> documentContentList) {
        return documentContentList.stream()
                .map(this::toContentDto)
                .toList();
    }

    default List<DocumentElementDtos.Editor> toEditorDtoList(List<DocumentEntity.Editor> documentEditorList) {
        return documentEditorList.stream()
                .map(this::toEditorDto)
                .toList();
    }

    default List<DocumentElementDtos.Detail> toDetailDtoList(List<DocumentEntity.Detail> documentDetailList) {
        return documentDetailList.stream()
                .map(this::toDetailDto)
                .toList();
    }
}

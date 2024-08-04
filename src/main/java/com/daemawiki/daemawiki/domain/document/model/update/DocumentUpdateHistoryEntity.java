package com.daemawiki.daemawiki.domain.document.model.update;

import com.daemawiki.daemawiki.domain.document.model.update.detail.DocumentInfoUpdateHistory;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "document_update_history")
@Getter(value = AccessLevel.PUBLIC)
public class DocumentUpdateHistoryEntity {

    @Id
    private String id;

    private String documentId;

    private String userId;

    private LocalDateTime updatedDataTime;

    private DocumentInfoUpdateHistory infoUpdateHistory;
}

package com.daemawiki.daemawiki.domain.document.model.update.detail;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfoDetail;

import java.util.List;

public record DocumentInfoUpdateHistory(
        String originalSubTitle,
        String newSubTitle,
        List<String> removedDetail,
        List<DocumentsInfoDetailUpdateHistory> updatedDetails,
        List<DocumentInfoDetail> addedDetails
) {
}
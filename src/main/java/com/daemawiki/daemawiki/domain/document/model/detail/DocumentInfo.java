package com.daemawiki.daemawiki.domain.document.model.detail;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor(staticName = "of")
public class DocumentInfo {
    private String subTitle;
    private List<DocumentInfoDetail> details;

    public void updateSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void updateDetails(List<DocumentInfoDetail> details) {
        this.details = details;
    }
}

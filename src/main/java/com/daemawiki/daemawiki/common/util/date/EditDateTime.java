package com.daemawiki.daemawiki.common.util.date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class EditDateTime {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
    private final LocalDateTime created;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
    private LocalDateTime updated;

    public void updateUpdatedDateTime() {
        this.updated = LocalDateTime.now();
    }

    public static EditDateTime getNewInstance() {
        var now = LocalDateTime.now();
        return of(now, now);
    }
}
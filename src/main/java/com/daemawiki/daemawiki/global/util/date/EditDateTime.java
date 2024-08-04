package com.daemawiki.daemawiki.global.util.date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class EditDateTime {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
    private final LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
    private LocalDateTime updated;

    public void updateUpdatedDateTime() {
        this.updated = LocalDateTime.now();
    }

    public static EditDateTime getNowInstance() {
        var now = LocalDateTime.now();
        return of(now, now);
    }
}
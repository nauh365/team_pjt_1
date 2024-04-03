package com.likelion.sixsenses.dto;
import lombok.Getter;

import com.likelion.sixsenses.entity.Notice;

@Getter
public class NoticeResponse {
    private final String title;
    private final String content;
    private final String writer;

    public NoticeResponse(Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.writer = notice.getWriter();
    }
}

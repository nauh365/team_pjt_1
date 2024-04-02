package com.likelion.sixsenses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateNoticeRequest {
    private String title;
    private String content;
    private String writer;
}

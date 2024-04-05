package com.likelion.sixsenses.dto;

import lombok.*;
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {
    private Long id;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private String writer;

}

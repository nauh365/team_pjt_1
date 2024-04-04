package com.likelion.sixsenses.dto;

import com.likelion.sixsenses.entity.Notice;
import jdk.jshell.Snippet;
import lombok.*;
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDto {
    private Long id;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private String writer;

//    public Notice toEntity(String writer) {
//        return Notice.builder()
//                .title(title)
//                .content(content)
//                .writer(writer)
//                .build();
//    }
}

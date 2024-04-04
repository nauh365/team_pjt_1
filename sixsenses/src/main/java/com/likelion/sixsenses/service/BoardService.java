package com.likelion.sixsenses.service;

import com.likelion.sixsenses.dto.NoticeDto;
import com.likelion.sixsenses.entity.Notice;
import com.likelion.sixsenses.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {
  private final NoticeRepository noticeRepository;

  @Transactional
  public List<NoticeDto> searchPosts(String keyword){
    List<Notice> notices = noticeRepository.findByTitleContaining(keyword);
    List<NoticeDto> noticeDtoList = new ArrayList<>();

    if (notices.isEmpty()) return noticeDtoList;

    for (Notice notice : notices){
      noticeDtoList.add(this.convertEntityToDto(notice));
    }
    return noticeDtoList;
  }

  private NoticeDto convertEntityToDto(Notice notice){
    return NoticeDto.builder()
            .id(notice.getId())
            .title(notice.getTitle())
            .content(notice.getContent())
            .writer(notice.getWriter())
            .build();
  }




}

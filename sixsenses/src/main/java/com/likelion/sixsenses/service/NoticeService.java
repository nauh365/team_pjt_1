package com.likelion.sixsenses.service;


import com.likelion.sixsenses.dto.NoticeDto;
import com.likelion.sixsenses.entity.Notice;
import com.likelion.sixsenses.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }

    public List<Notice> findAll() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id")

        );
    }

    public Notice findById(long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        noticeRepository.deleteById(id);
    }

    public void update(
            Long id,
            String title,
            String content,
            String writer
    ) {
        Notice target
                = noticeRepository.findById(id).orElse(new Notice());
        target.setTitle(title);
        target.setContent(content);
        target.setWriter(writer);
        noticeRepository.save(target);
    }

    // 검색 기능
    @Transactional
    public List<NoticeDto> searchPosts(String keyword) {
        List<Notice> notices = noticeRepository.findByTitleContaining(keyword);
        List<NoticeDto> noticeDtoList = new ArrayList<>();

        if (notices.isEmpty()) return noticeDtoList;

        for (Notice notice : notices) {
            noticeDtoList.add(this.convertEntityToDto(notice));
        }

        return noticeDtoList;
    }

    private NoticeDto convertEntityToDto(Notice notice) {
        return NoticeDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter())
                .build();
    }
}
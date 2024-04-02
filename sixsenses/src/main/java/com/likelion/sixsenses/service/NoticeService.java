package com.likelion.sixsenses.service;


import com.likelion.sixsenses.entity.Notice;
import com.likelion.sixsenses.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



import java.util.List;


@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    public Notice save(String title, String content, String writer) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setWriter(writer);
        return noticeRepository.save(notice);
    }

    public List<Notice> findAll() {
        return noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
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
}
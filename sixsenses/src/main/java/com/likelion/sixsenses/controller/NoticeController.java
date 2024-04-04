package com.likelion.sixsenses.controller;

import com.likelion.sixsenses.dto.NoticeDto;
import com.likelion.sixsenses.dto.NoticeResponse;
import com.likelion.sixsenses.entity.Notice;
import com.likelion.sixsenses.service.BoardService;
import com.likelion.sixsenses.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final BoardService boardService;


    // 공지사항 페이지
    @GetMapping("/notice/list")
    public String homeView(Model model) {
        model.addAttribute("notices", noticeService.findAll());
        return "allNoticeList";
    }

    // 공지사항 세부내용
    @GetMapping("/notice/detail/{id}")
    public String readOneView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("notice", noticeService.findById(id));
        return "noticeDetail";
    }

    // 공지사항 새글 작성 폼
    @GetMapping("notice/add")
    public String addView() {
        return "noticeAdd";
    }

    @PostMapping("/api/addNotice")
    public String addNotice(Notice notice
    ) {
        noticeService.save(notice);
        return "redirect:/notice/list";
    }

    @GetMapping("/notice/update/{id}")
    public String updateView(
            @PathVariable("id")
            Long id,
            Model model
    ) {
        model.addAttribute("notice", noticeService.findById(id));
        return "noticeModify";
    }

    // 공지사항 업데이트
    @PostMapping("/api/notice/update/{id}")
    public String updateNotice(
            @PathVariable("id") Long id, Notice notice
    ) {
        Notice noticeTemp = noticeService.findById(id);
        noticeTemp.setTitle(notice.getTitle());
        noticeTemp.setContent(notice.getContent());
        noticeTemp.setWriter(notice.getWriter());

        noticeService.save(noticeTemp);
        return String.format("redirect:/notice/detail/%d", id);
    }

    @GetMapping("/api/notices")
    public ResponseEntity<List<NoticeResponse>> findAllNotices() {
        List<NoticeResponse> notices = noticeService.findAll()
                .stream()
                .map(NoticeResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(notices);
    }

    @GetMapping("/api/notice/{id}")
    public ResponseEntity<NoticeResponse> findNotice(@PathVariable Long id) {
        Notice notice = noticeService.findById(id);

        return ResponseEntity.ok()
                .body(new NoticeResponse(notice));
    }

    @GetMapping("/api/notice/delete/{id}")
    public String deleteNotice(
            @PathVariable("id") Long id) {
        noticeService.delete(id);
        return "redirect:/notice/list";
    }

    @GetMapping("/test")
    public String Test(
        @RequestParam(value = "keyword")
        String keyword,
        Model model
    ) {
        List<NoticeDto> noticeDtos = boardService.searchPosts(keyword);
        log.info("@@@@@@ : {}", noticeDtos);
        model.addAttribute("notice", noticeDtos);

        return "test";
    }
}

package com.likelion.sixsenses.controller;

import com.likelion.sixsenses.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AnswerController {
    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/POST/answer/new")
    public String createAnswer(
            Long questionId,
            String authorId,
            String content
    ) {
        answerService.saveAnswer(questionId, authorId, content);
        return "redirect:/GET/question/" + questionId;
    }

    @PostMapping("/POST/answer/{answerId}/update")
    public String updateAnswer(
            Long questionId,
            @PathVariable Long answerId,
            @RequestParam String content,
            RedirectAttributes redirectAttributes) {
        answerService.updateAnswer(answerId, content);
        redirectAttributes.addFlashAttribute("message", "답변이 성공적으로 수정되었습니다.");
        return "redirect:/GET/question/" + questionId;
    }


    @PostMapping("/POST/answer/{answerId}/delete")
    public String deleteAnswer(
            Long questionId,
            @PathVariable Long answerId,
            RedirectAttributes redirectAttributes) {
        answerService.deleteAnswer(questionId,answerId);
        redirectAttributes.addFlashAttribute("message", "답변이 성공적으로 삭제되었습니다.");
        return "redirect:/GET/question/" + questionId;
    }
}

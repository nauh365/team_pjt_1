package com.likelion.sixsenses.controller;

import com.likelion.sixsenses.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

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
}

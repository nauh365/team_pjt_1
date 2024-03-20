package com.likelion.sixsenses.controller;

import com.likelion.sixsenses.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/GET/questions")
    public String listQuestions (Model model) {
        model.addAttribute("questions", questionService.findAllQuestions());
        return "questions";
    }

}

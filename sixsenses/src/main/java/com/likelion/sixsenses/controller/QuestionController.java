package com.likelion.sixsenses.controller;

import com.likelion.sixsenses.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/GET/question")
    public String listQuestions (Model model) {
        model.addAttribute("questions", questionService.findAllQuestions());
        return "questions";
    }

    @GetMapping("/GET/question/{id}")
    public String showQuestionDetail(@PathVariable Long id, Model model) {
        questionService.findQuestionById(id).ifPresent(question -> model.addAttribute("question", question));
        return "questionDetail";
    }

}

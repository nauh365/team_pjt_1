package com.likelion.sixsenses.controller;

import com.likelion.sixsenses.entity.Question;
import com.likelion.sixsenses.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
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

    @GetMapping("/GET/question/new")
    public String showCreateQuestionForm(Model model) {
        // model.addAttribute("question", new Question()); // 필요한 경우 빈 Question 객체를 모델에 추가
        return "createQuestion"; // HTML 파일 이름에 맞추어 변경하세요.
    }

    @PostMapping("/POST/question/new")
    public String createQuestion(
            @ModelAttribute Question question,
            RedirectAttributes redirectAttributes
    ) {
        log.info("Received question: {}", question);
        questionService.createQuestion(question);
        redirectAttributes.addFlashAttribute("message", "질문이 성공적으로 등록되었습니다.");
        return "redirect:/GET/question"; // 질문 목록 페이지로 리다이렉트
    }

    @GetMapping("/GET/question/{id}/update")
    public String showUpdateForm(
            @PathVariable Long id,
            Model model) {
        //TODO 에러처리
        Question question = questionService.findQuestionById(id).orElseThrow(
                () -> new IllegalArgumentException("Inva")
        );
        model.addAttribute("question", question); // 필요한 경우 빈 Question 객체를 모델에 추가
        return "updateQuestion"; // HTML 파일 이름에 맞추어 변경하세요.
    }

    @PostMapping("/POST/question/{id}/update")
    public String updateQuestion(
            @PathVariable Long id,
            @ModelAttribute Question updateQuestion,
            RedirectAttributes redirectAttributes
    ) {
        questionService.updateQuestion(id, updateQuestion);
        redirectAttributes.addFlashAttribute("message", "질문이 성공적으로 업데이트되었습니다.");
        return "redirect:/GET/question"; // 질문 목록 페이지로 리다이렉트
    }

    @PostMapping("/POST/question/{id}/delete")
    public String deleteQuestion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        questionService.deleteQuestion(id);
        redirectAttributes.addFlashAttribute("message", "질문이 성공적으로 삭제되었습니다.");
        return "redirect:/GET/question";
    }


}

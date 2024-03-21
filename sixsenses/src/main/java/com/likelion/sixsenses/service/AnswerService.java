package com.likelion.sixsenses.service;

import com.likelion.sixsenses.entity.Answer;
import com.likelion.sixsenses.entity.Question;
import com.likelion.sixsenses.repository.AnswerRepository;
import com.likelion.sixsenses.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public void saveAnswer(
            Long questionId,
            String authorId,
            String content
    ) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question Id:" + questionId));
        Answer answer = Answer.builder()
                .content(content)
                .authorId(authorId)
                .question(question)
                .createdAt(LocalDateTime.now()) // 혹시 @PrePersist가 작동하지 않을 경우를 대비해 명시적으로 설정
                .build();
        answerRepository.save(answer);
        question.setAnswerStatus("답변완료");
        questionRepository.save(question);
    }

}

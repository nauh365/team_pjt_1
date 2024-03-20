package com.likelion.sixsenses.service;

import com.likelion.sixsenses.entity.Question;
import com.likelion.sixsenses.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }

    @Transactional
    public Optional<Question> findQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

}

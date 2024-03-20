package com.likelion.sixsenses.repository;

import com.likelion.sixsenses.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}

package com.likelion.sixsenses.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 255)
    private String title;

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Setter
    @Column(nullable = false, name = "author_id")
    private String authorId;

    @Setter
    @Column(nullable = false, name = "answer_status", length = 50)
    private String answerStatus = "미답변";

    @Setter
    @Column(nullable = false, name = "created_at") // 생성 시간
    private LocalDateTime createdAt;

    @Setter
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answer;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}

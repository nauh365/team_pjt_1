package com.likelion.sixsenses.book.entity;

import com.likelion.sixsenses.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class ReserveHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rentalHistoryId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private LocalDateTime expireDate;

    @Builder
    public ReserveHistory(Book book, UserEntity user){
        this.book = book;
        this.user = user;
        this.expireDate = LocalDateTime.now().plusDays(3);
    }

    public static ReserveHistory borrow(Book book, UserEntity user){
        return new ReserveHistory(book, user);
    }
}

package com.likelion.sixsenses.book.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String ISBN;

    private int libraryId;
    private int quantity;

    private String bookTitle;

    @Column(columnDefinition = "TEXT")
    private String bookSummary;

    private String bookPublisher;

    private String bookWriter;

    private String bookCover;

    private String bookDate;

    public int getQuantity(){
        return quantity;
    }

    public Book(Long bookId, int quantity){
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public void decreaseQuantity(){
        if(this.quantity == 0) {
            throw new RuntimeException("수량이 0개 입니다. 대출할 수 없습니다.");
        }
        --this.quantity;
    }
}

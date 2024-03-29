package com.likelion.sixsenses.book.dto.response;

import com.likelion.sixsenses.book.entity.Book;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookDetailDto {
    private Long bookId;

    private boolean canBorrow;

    private String bookTitle;

    private String bookSummary;

    private String bookPublisher;

    private String bookWriter;

    private String bookCover;

    private String bookDate;

    @Builder
    public BookDetailDto(Book book){
        this.bookId = book.getBookId();
        this.bookCover = book.getBookCover();
        this.bookDate = book.getBookDate();
        this.bookPublisher = book.getBookPublisher();
        this.bookTitle = book.getBookTitle();
        this.bookSummary = book.getBookSummary();
        this.bookWriter = book.getBookWriter();
        this.canBorrow = book.getQuantity() > 0 ? true : false;
    }

    public static BookDetailDto from(Book book){
        return BookDetailDto.builder().book(book).build();
    }
}

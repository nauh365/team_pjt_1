package com.likelion.sixsenses.book.service;

import com.likelion.sixsenses.book.dto.response.BookDetailDto;
import com.likelion.sixsenses.book.entity.Book;
import com.likelion.sixsenses.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public BookDetailDto getBookDetail(Long bookId){
        Book findBook = bookRepository.findById(bookId).orElseThrow();
        return BookDetailDto.from(findBook);
    }

    public void borrowBook(Long bookId){
        Book findBook = bookRepository.findById(bookId).orElseThrow();
        findBook.decreaseQuantity();

        bookRepository.save(findBook);
    }

    public synchronized void borrowBookWithSync(Long bookId){
        Book findBook = bookRepository.findById(bookId).orElseThrow();
        findBook.decreaseQuantity();

        bookRepository.save(findBook);
    }

    @Transactional
    public void borrowBookWithPessimisticLock(Long bookId){
        Book findBook = bookRepository.findByBookIdWithPessimisticLock(bookId).orElseThrow();
        findBook.decreaseQuantity();

        bookRepository.save(findBook);
    }
}

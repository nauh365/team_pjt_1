package com.likelion.sixsenses.book.service;

import com.likelion.sixsenses.book.dto.response.BookDetailDto;
import com.likelion.sixsenses.book.entity.Book;
import com.likelion.sixsenses.book.entity.ReserveHistory;
import com.likelion.sixsenses.book.repository.BookRepository;
import com.likelion.sixsenses.book.repository.ReserveHistoryRepository;
import com.likelion.sixsenses.entity.UserEntity;
import com.likelion.sixsenses.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final ReserveHistoryRepository reserveHistoryRepository;
    private final UserRepository userRepository;

    public BookDetailDto getBookDetail(Long bookId){
        Book findBook = bookRepository.findById(bookId).orElseThrow();
        return BookDetailDto.from(findBook);
    }

    @Transactional
    public void borrowBook(long bookId, long userId){
        Book findBook = bookRepository.findByBookIdWithOptimisticLock(bookId).orElseThrow();
        findBook.decreaseQuantity();
        bookRepository.save(findBook);


        UserEntity user = userRepository.findById(userId).orElseThrow();

        reserveHistoryRepository.save(ReserveHistory.borrow(findBook, user));
    }
}

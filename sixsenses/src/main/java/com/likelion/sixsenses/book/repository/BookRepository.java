package com.likelion.sixsenses.book.repository;

import com.likelion.sixsenses.book.entity.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Book as b where b.bookId = :bookId")
    Optional<Book> findByBookIdWithPessimisticLock(Long bookId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select b from Book as b where b.bookId = :bookId")
    Optional<Book> findByBookIdWithOptimisticLock(long bookId);

}

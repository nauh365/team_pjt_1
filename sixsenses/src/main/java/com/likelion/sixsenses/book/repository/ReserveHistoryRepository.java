package com.likelion.sixsenses.book.repository;

import com.likelion.sixsenses.book.entity.ReserveHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveHistoryRepository extends JpaRepository<ReserveHistory, Long> {
}

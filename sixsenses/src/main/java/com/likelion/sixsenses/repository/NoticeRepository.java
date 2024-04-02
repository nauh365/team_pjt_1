package com.likelion.sixsenses.repository;

import com.likelion.sixsenses.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository
        extends JpaRepository<Notice, Long> {
}
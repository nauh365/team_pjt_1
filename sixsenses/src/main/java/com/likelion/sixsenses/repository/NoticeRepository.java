package com.likelion.sixsenses.repository;

import com.likelion.sixsenses.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository
        extends JpaRepository<Notice, Long> {
  List<Notice> findByTitleContaining(String keyword);
}
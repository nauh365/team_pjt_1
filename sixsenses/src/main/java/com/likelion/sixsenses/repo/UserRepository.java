package com.likelion.sixsenses.repo;


import com.likelion.sixsenses.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  // UserDetailsManager 만들기
  Optional<UserEntity> findByUsername(String username);
  boolean existsByUsername(String username);
}

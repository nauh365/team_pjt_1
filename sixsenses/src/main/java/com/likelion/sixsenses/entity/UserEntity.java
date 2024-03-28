package com.likelion.sixsenses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class UserEntity {
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  // username은 null일 수 없고, 겹칠 수 없다
  @Column(nullable = false, unique = true)
  private String username;
  private String password;

  private String name;
  private String nickname;
  private String profile;
  private String email;
  // 전화번호
  private String mobile;

  private String authorities;

}

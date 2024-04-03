package com.likelion.sixsenses.jwt;

import lombok.Data;

@Data
// 클라이언트에서 받을 데이터의 Dto의 모습
public class JwtRequestDto
{
  private String username;
  private String password;
}

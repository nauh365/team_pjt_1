package com.likelion.sixsenses.jwt;

import lombok.Data;

@Data
// 서버에서 클라이언트로 돌려줄 Dto의 모습
// 클라이언트에서 받은 데이터를 암호화하여 JWT으로 돌려줌
public class JwtResponseDto {
  private String token;
}

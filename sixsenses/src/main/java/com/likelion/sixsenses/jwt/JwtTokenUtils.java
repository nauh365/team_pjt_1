package com.likelion.sixsenses.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
// JWT와 관련된 기능을 만드는 곳
public class JwtTokenUtils {
  private final Key signingKey;
  // 파싱
  private final JwtParser jwtParser;

  public JwtTokenUtils(
        @Value("${jwt.secret}")
        String jwtSecret
  ){
    this.signingKey
            = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    // JWT를 해석하는 용도의 객체
    this.jwtParser = Jwts
            .parserBuilder()
            .setSigningKey(this.signingKey)
            .build();
  }

  // 토큰 만들기
  public String generateToken(UserDetails userDetails){
    Instant now = Instant.now();
    Claims jwtClaims = Jwts.claims()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from((now.plusSeconds(60*60*24*7))));

    // 최종적으로 JWT를 발급한다.
    return Jwts.builder()
            .setClaims(jwtClaims)
            .signWith(this.signingKey)
            .compact();
  }

  // 정상적인 JWT인지를 판단하는 메서드
  public boolean validate(String token) {
    try {
      jwtParser.parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      log.warn(e.getMessage());
      log.warn("invalid jwt");
    }
    return false;
  }

  // 실제 데이터(Payload)를 반환하는 메서드
  public Claims parseClaims(String token) {
    return jwtParser
            .parseClaimsJws(token)
            .getBody();
  }
}

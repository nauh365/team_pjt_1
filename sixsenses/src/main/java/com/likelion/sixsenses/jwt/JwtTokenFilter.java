package com.likelion.sixsenses.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
// 유효한 토큰인지 확인하고 생성 및 등록을 하는 곳
public class JwtTokenFilter extends OncePerRequestFilter {
  private final JwtTokenUtils jwtTokenUtils;
  private final UserDetailsManager manager;

  public JwtTokenFilter(
          JwtTokenUtils jwtTokenUtils,
          UserDetailsManager manager
  ) {
    this.jwtTokenUtils = jwtTokenUtils;
    this.manager = manager;
  }

  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
  ) throws ServletException, IOException {

    log.debug("try jwt filter");

    // FIXME: Http Request Header 부분에서 AUTHORIZATION을 통해 권한검사는 어떻게 하는지

    String authHeader
            = request.getHeader(HttpHeaders.AUTHORIZATION);

//    헤더에 들어가있는 토큰 찍어보기
    log.info("@@ : " + authHeader);

    if (authHeader != null && authHeader.startsWith("Bearer")) {
      String token = authHeader.split(" ")[1];
      log.info("@@@ : " + token);
      log.info(SecurityContextHolder.getContext().toString());
      // Token이 유효한 토큰인지를 검사
      if (jwtTokenUtils.validate(token)){
        // 토큰이 유효하다면 해당 Token을 바탕으로 사용자 정보를 SecurityContext에 등록
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // 사용자 정보 회수
        String username = jwtTokenUtils
                .parseClaims(token)
                .getSubject();

        //loadUserByUsername = 내부에서 인증 처리할 때 사용
        UserDetails userDetails = manager.loadUserByUsername(username);
        for (GrantedAuthority authority : userDetails.getAuthorities()){
          log.info("authority {}",authority.getAuthority());
        }

        // 인증 정보 생성
        AbstractAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        token,
                        // 인증을 하고나서, 권한이 들어가는 곳
                        userDetails.getAuthorities()
                );
        // 인증 정보 등록
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        log.info(SecurityContextHolder.getContext().toString());
        log.info("set security context with jwt");
      } else {
        log.warn("jwt validation failed");
      }
    }
    // doFilter를 호출하지 않으면 Controller까지 요청이 도달하지 못한다.
    filterChain.doFilter(request, response);
  }
}

package com.likelion.sixsenses.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.sixsenses.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

  private final JwtTokenUtils jwtTokenUtils;
  // nickname가져올 때 사용
  private final ObjectMapper objectMapper;

  @GetMapping("/login")
  public String loginForm() {
    return "login-form";
  }

  @GetMapping("main")
  public String mainPage(
          @RequestParam(value = "token", required = false)
          String token,
          Model model
  ) {
    if (jwtTokenUtils.validate(token)) {
      // 사용자 정보 회수
      String username = jwtTokenUtils
              .parseClaims(token)
              .getSubject();

      model.addAttribute("username", username);
      log.info("token :" + token);
      model.addAttribute("token", token);
      return "main";
    }
    else {
      log.warn("로그인 전");
      return "main";
    }
  }

  @GetMapping("read")
  public String profile(
  ){
    return "read";
  }

  @GetMapping("write")
  public String write(
  ){
    return "write";
  }


//  fetch(script)를 사용하는 방법 (SecurityFilter에 토큰이 전달되는게 로그에 찍히자만, getUser에는 토큰이 찍히지 않음)
  @GetMapping(path="/getUser", headers="AUTHORIZATION")
  @ResponseBody
  public Object getUser() {
    log.info("####### + {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    String auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
    Map<String, String> body = new HashMap<>();
    body.put("username", name);
    body.put("auth", auth);

    return body;
  }
  // jwt , 상태를 저장하지 않는 인증

}
package com.likelion.sixsenses.controller;

import com.likelion.sixsenses.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsManager userDetailsManager;
    @GetMapping("/home")
    public String loginForm(
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
            return "index";
        }
        else {
            log.warn("로그인 전");
            return "index";
        }
    }
}

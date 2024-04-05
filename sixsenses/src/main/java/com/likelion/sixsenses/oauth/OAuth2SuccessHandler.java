package com.likelion.sixsenses.oauth;

import com.likelion.sixsenses.entity.CustomUserDetails;
import com.likelion.sixsenses.jwt.JwtTokenUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OAuth2SuccessHandler
        // 인증에 성공했을 때 특정 URL로 리다이렉트 하고 싶은 경우 활용 가능한 SuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
  // JWT 발급을 위해 JwtTokenUtils
  private final JwtTokenUtils tokenUtils;
  // 사용자 정보 등록을 위해 UserDetailsManager
  private final UserDetailsManager userDetailsManager;

  @Override //인증에 성공했을때 ↓메서드 실행
  public void onAuthenticationSuccess(
          HttpServletRequest request,
          HttpServletResponse response,
          Authentication authentication
  ) throws IOException, ServletException {


    // OAuth2UserServiceImpl클래스의 반환값이 저장된다
    OAuth2User oAuth2User
            = (OAuth2User) authentication.getPrincipal();

    // 1. 넘겨받은 정보를 바탕으로 사용자 정보를 준비
    String name = oAuth2User.getAttribute("name");
    String nickname = oAuth2User.getAttribute("nickname");
    String email = oAuth2User.getAttribute("email");
    String profile = oAuth2User.getAttribute("profile");
    String mobile = oAuth2User.getAttribute("mobile");

    // 어디서 넘겨 받았는지
    String provider = oAuth2User.getAttribute("provider");

    String username
            = String.format("{%s}%s", provider, email);

    String providerId = oAuth2User.getAttribute("id").toString();
    /* 처음으로 이 소셜 로그인으로 로그인을 시도했다
     userDetailsManager에 username이 존재하지 않는다면 */
     if (!userDetailsManager.userExists(username)){
      // 새 계정을 만들어야 한다
      userDetailsManager.createUser(CustomUserDetails.builder()
              .username(username)
              .password(providerId)
              .name(name)
              .nickname(nickname)
              .email(email)
              .profile(profile)
              .mobile(mobile)
              .authorities("ROLE_USER")
              .build());
    }

    // 데이터 베이스에서 사용자 계정 회수
    UserDetails details
            =userDetailsManager.loadUserByUsername(username);
    // JWT 생성
    String jwt = tokenUtils.generateToken(details);

    String targetUrl = String.format(
            "http://localhost:8089/home?token=%s", jwt
    );

//    details.getAuthorities();


    // FIXME: Spring(Security) Context Holder, Pricnipal 객체가 무엇인지?
    // SecurityContext = Authentication이 보관되는 보관소, 코드 어디서나 Authentication을 꺼내 사용 가능
    // Pricnipal = 인증된 사용자 정보, Authentication이 관리
    // SecurityContextHolder > SecurityContext > Authentication > Principal
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
}

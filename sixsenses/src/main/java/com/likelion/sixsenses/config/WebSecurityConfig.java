package com.likelion.sixsenses.config;


import com.likelion.sixsenses.jwt.JwtTokenFilter;
import com.likelion.sixsenses.jwt.JwtTokenUtils;
import com.likelion.sixsenses.oauth.OAuth2SuccessHandler;
import com.likelion.sixsenses.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtTokenUtils jwtTokenUtils;
  private final UserDetailsManager manager;
  private final OAuth2UserServiceImpl oAuth2UserService;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;


  @Bean
  public SecurityFilterChain securityFilterChain(
          HttpSecurity http
  ) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    // 로그인
                    .requestMatchers(
                            "/users/login",
                            "/home"
                    )
                    .anonymous()

                    // 모두 접근 가능
                    .requestMatchers(
                            "/users/main",
                            "/users/read",
                            // 공지사항
                            "/notice/**",
                            "/api/addNotice",
                            "/api/notices",
                            "/api/notice/{id}",
                            "/api/notice/update/{id}",
                            "/api/notice/delete/{id}",
                            "/notice/search",
                            //도서상세
                            "/books/**",
                            "/library/**",
//                            현준님 css
                            "/",
                            "/css/**",
                            "/js/**",
                            "/lib/**",
                            "/scss/**",
                            "/img/**"
                    )
                    .permitAll()
                    // 로그인 후 접근 가능
                    .requestMatchers(
                            "/users/write",
                            "/GET/**",
                            "/POST/**"
                    )
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            )

            .oauth2Login(oauth2Login -> oauth2Login
                            .successHandler(oAuth2SuccessHandler)
                            .loginPage("/users/login")
                            .userInfoEndpoint(userInfo -> userInfo
                                    .userService(oAuth2UserService))
            )

//           FIXME: Stateless가 무엇인지?
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            /*
            Stateless = 클라이언트의 요청을 서버에서 상태를 기록하지 않는 접속 방식
            즉, 로그인 후 다시 웹페이지에 접근하면 로그인 상태가 유지되지 않는다.
             */

            .addFilterBefore(
                    new JwtTokenFilter(
                            jwtTokenUtils,
                            manager
                    ),
                    AuthorizationFilter.class
            );

    return http.build();
  }
}


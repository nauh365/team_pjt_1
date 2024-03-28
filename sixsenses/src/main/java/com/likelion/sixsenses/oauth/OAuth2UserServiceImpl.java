package com.likelion.sixsenses.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OAuth2UserServiceImpl
        extends DefaultOAuth2UserService {
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    String registrationId = userRequest
            .getClientRegistration()
            .getRegistrationId();

    Map<String, Object> attribute = new HashMap<>();
    String nameAttribute = " ";

    // Kakao 로그인
    if (registrationId.equals("kakao")){
      log.info(attribute.toString());
      attribute.put("provider", "kakao");

      attribute.put("id", oAuth2User.getAttribute("id"));

      Map<String, Object> kakaoAccount
              = oAuth2User.getAttribute("kakao_account");
      attribute.put("email", kakaoAccount.get("email"));

      Map<String, Object> kakaoProfile
              = (Map<String, Object>) kakaoAccount.get("profile");
      attribute.put("nickname", kakaoProfile.get("nickname"));
      attribute.put("profileImg", kakaoProfile.get("profile_image_url"));
      nameAttribute = "email";
    }


    // Naver 로그인
    if (registrationId.equals("naver")){
      attribute.put("provider", "naver");

      Map<String, Object> responseMap
              // 네이버가 반환한 Json에서 response 회수
              = oAuth2User.getAttribute("response");

      attribute.put("id", responseMap.get("id"));

      attribute.put("name", responseMap.get("name"));
      attribute.put("email", responseMap.get("email"));
      attribute.put("nickname", responseMap.get("nickname"));
      attribute.put("profile", responseMap.get("profile_image"));
      attribute.put("mobile", responseMap.get("mobile"));

      nameAttribute = "email";
    }
    return new DefaultOAuth2User(
            // 권한 설정
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            // 위에서 정의한 attribute 정보
            attribute,
            nameAttribute
    );
  }
}
// OAuth2(소셜로그인)에 인증이 성공하면 FrontEnd가 그 사실을 알고 jwt를 넘겨 받아야 한다

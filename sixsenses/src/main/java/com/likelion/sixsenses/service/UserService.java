package com.likelion.sixsenses.service;


import com.likelion.sixsenses.entity.CustomUserDetails;
import com.likelion.sixsenses.entity.UserEntity;
import com.likelion.sixsenses.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsManager {
  private final UserRepository userRepository;


  // 테스트 목적으로 @RequiredArgsConstructor를 지우고 생성자를 생성해봄
  public UserService(
          UserRepository userRepository,
          PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    if (!userExists("admin"))
    createUser(CustomUserDetails.builder()
            .username("admin")
            .password(passwordEncoder.encode("password"))
            .name("관리자")
            .nickname("관리자")
            .email("admin@gmail.com")
            .profile("준비중")
            .mobile("010-1234-5678")
            .authorities("ROLE_USER, ROLE_ADMIN")
            .build());
  }

  @Override
  public UserDetails loadUserByUsername(String username)
          throws UsernameNotFoundException {
    // UserEntity에서 username을 찾아온다
    Optional<UserEntity> optionalUser
            = userRepository.findByUsername(username);
    if (optionalUser.isEmpty())
      throw new UsernameNotFoundException(username);

    // CustomUserDetails 이용하기
    UserEntity userEntity = optionalUser.get();
    return CustomUserDetails.builder()
            .username(userEntity.getUsername())
            .password(userEntity.getPassword())
            .name(userEntity.getName())
            .email(userEntity.getEmail())
            .mobile(userEntity.getMobile())
            .authorities(userEntity.getAuthorities())
            .build();

  }
  
  @Override
  // 편의를 위해 같은 규약으로 회원가입을 하는 메서드
  public void createUser(UserDetails user) {
    // getUsername는 UserDetails에서 가져옴
    // userExists()는 아래 메서드에서 정의
    if (userExists(user.getUsername()))
      // 이미 존재하는 유저라면 BAD_REQUEST
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    // CustomUserDetails(dto) 이용하기
    try{
      CustomUserDetails userDetails =
              (CustomUserDetails) user;
      UserEntity newUser = UserEntity.builder()
              .username(userDetails.getUsername())
              .password(userDetails.getPassword())
              .name(userDetails.getName())
              .nickname(userDetails.getNickname())
              .email(userDetails.getEmail())
              .profile(userDetails.getProfile())
              .mobile(userDetails.getMobile())
              .authorities(userDetails.getRawAuthorities())
              .build();
      userRepository.save(newUser);
    } catch (ClassCastException e){
      log.error("Failed Cast to: {}", CustomUserDetails.class);
      // INTERNAL_SERVER_ERROR = 개발자가 잘못 사용하여 에러가 났다
      throw  new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public boolean userExists(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public void updateUser(UserDetails user) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public void deleteUser(String username) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

}



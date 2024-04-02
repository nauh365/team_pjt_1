package com.likelion.sixsenses.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CustomUserDetails implements UserDetails {
  @Getter
  private Long id;
  private String username;
  private String password;

  @Getter
  private String name;
  @Getter
  private String nickname;
  @Getter
  private String email;
  @Getter
  private String profile;
  @Getter
  private String mobile;
  
  // 권한 데이터를 담기 위한 속성
  private String authorities;


  public String getRawAuthorities(){
    return this.authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> grantedAuthorities
            = new ArrayList<>();
    String[] rawAuthorities = authorities.split(",");
    for (String rawAuthority: rawAuthorities) {
      grantedAuthorities.add(new SimpleGrantedAuthority(rawAuthority));
    }
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

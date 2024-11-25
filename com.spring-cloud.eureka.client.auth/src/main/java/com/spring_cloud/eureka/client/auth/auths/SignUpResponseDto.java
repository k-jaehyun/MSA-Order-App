package com.spring_cloud.eureka.client.auth.auths;

import com.spring_cloud.eureka.client.auth.core.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {

  private String username;

  private String role;

  public SignUpResponseDto(User user) {
    this.username = user.getUsername();
    this.role = user.getRole().toString();
  }
}

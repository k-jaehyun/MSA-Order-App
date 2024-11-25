package com.spring_cloud.eureka.client.auth.auths;

import com.spring_cloud.eureka.client.auth.core.User;
import com.spring_cloud.eureka.client.auth.core.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDto {

  private String username;

  private UserRoleEnum role;

  public SignInResponseDto(User user) {
    this.username = user.getUsername();
    this.role = user.getRole();
  }
}

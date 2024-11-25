package com.spring_cloud.eureka.client.auth.auths;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestDto {

  private String username;

  private String password;

}

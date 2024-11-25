package com.spring_cloud.eureka.client.auth.auths;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signUp")
  public ResponseEntity signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

    SignUpResponseDto signUpResponseDto = authService.signUp(signUpRequestDto);

    return ResponseEntity.ok().body(signUpResponseDto);
  }


}

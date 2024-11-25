package com.spring_cloud.eureka.client.auth.auths;

import com.spring_cloud.eureka.client.auth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
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
  private final JwtUtil jwtUtil;

  @PostMapping("/signUp")
  public ResponseEntity signUp(
      @RequestBody SignUpRequestDto signUpRequestDto) {

    SignUpResponseDto signUpResponseDto = authService.signUp(signUpRequestDto);

    return ResponseEntity.ok(signUpResponseDto);
  }

  @PostMapping("/signIn")
  public ResponseEntity signIn(
      @RequestBody SignInRequestDto signInRequestDto,
      HttpServletResponse response) {

    SignInResponseDto signInResponseDto = authService.signIn(signInRequestDto);

    String token = jwtUtil
        .createToken(signInResponseDto.getUsername(), signInResponseDto.getRole());

    jwtUtil.addJwtToCookie(token, response);

    return ResponseEntity.ok(signInResponseDto);
  }


}

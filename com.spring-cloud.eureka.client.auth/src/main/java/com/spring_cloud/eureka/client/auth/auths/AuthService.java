package com.spring_cloud.eureka.client.auth.auths;

import com.spring_cloud.eureka.client.auth.core.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
    String username = signUpRequestDto.getUsername();
    String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

    if (userRepository.findUserByUsername(username).isPresent()) {
      throw new IllegalArgumentException(username + " : 이미 존재하는 username 입니다.");
    }

    User user = SignUpRequestDto.toEntity(username, encodedPassword);
    userRepository.save(user);

    return new SignUpResponseDto(user);
  }

  public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {

    String username = signInRequestDto.getUsername();
    String password = signInRequestDto.getPassword();

    User user = userRepository.findUserByUsername(username)
        .orElseThrow(
            () -> new IllegalArgumentException(username + " : 존재하지 않는 username입니다.")
        );

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    return new SignInResponseDto(user);
  }
}

package com.spring_cloud.eureka.client.gateway.filter;

import com.spring_cloud.eureka.client.gateway.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements GlobalFilter, Ordered {

  //  private final AuthService authService; // 순환 참조 오류
  private final JwtUtil jwtUtil;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpResponse response = exchange.getResponse();

    // 로그인, 회원가입 경로 예외
    String path = exchange.getRequest().getURI().getPath();
    if (path.equals("/auth/signIn") || path.equals("/auth/signUp")) {
      return chain.filter(exchange);
    }

    String bearerToken = jwtUtil.getTokenFromRequest(exchange.getRequest());

    if (StringUtils.hasText(bearerToken)) {
      String tokenValue = jwtUtil.substringToken(bearerToken);

      if (tokenValue == null || !jwtUtil.validateToken(tokenValue)) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }

      Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

      String username = info.getSubject();
      String role = info.get(JwtUtil.ROLE).toString();

      // 순환 참조 오류
//      if (role.equals("ROLE_ADMIN")) {
//        if(!authService.isAdminUser(username)) {
//          throw new RuntimeException("Admin이 아닙니다.");
//        }
//      }

      response.getHeaders().add("username", username);
      response.getHeaders().add(JwtUtil.ROLE, role);
      response.getHeaders().add("issuer", info.getIssuer());
      response.getHeaders().add("issuedAt", info.getIssuedAt().toString());
    } else {
      throw new RuntimeException("token has no text");
    }

    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }

}

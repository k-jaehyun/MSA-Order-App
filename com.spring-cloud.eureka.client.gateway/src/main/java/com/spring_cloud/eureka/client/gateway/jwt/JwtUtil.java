package com.spring_cloud.eureka.client.gateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String ROLE = "Role";
  public static final String BEARER_PREFIX = "Bearer ";

  @Value("${spring.application.name}")
  private String issuer;

  @Value("${service.jwt.access-expiration}")
  private Long TOKEN_TIME;

  @Value("${service.jwt.secret-key}")
  private String secretKey;
  private SecretKey key;
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  public void addJwtToCookie(String token, HttpServletResponse res) {
    try {
      token = URLEncoder.encode(token, "utf-8")
          .replaceAll("\\+", "%20");

      Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
      cookie.setPath("/");

      res.addCookie(cookie);
    } catch (UnsupportedEncodingException e) {
      System.out.println(e.getMessage());
    }
  }

  public String getTokenFromRequest(ServerHttpRequest req) {
    HttpCookie cookie = req.getCookies().getFirst(AUTHORIZATION_HEADER);
    try {
      return URLDecoder.decode(cookie.getValue(), "UTF-8");
    } catch (Exception e) {
      return null;
    }
  }

  public String substringToken(String tokenValue) {
    if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
      return tokenValue.substring(7);
    }
    throw new IllegalArgumentException("Not Found Token");
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claimsJws = Jwts.parser()
          .verifyWith(key)
          .build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Claims getUserInfoFromToken(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }
}
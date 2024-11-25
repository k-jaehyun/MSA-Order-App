package com.spring_cloud.eureka.client.auth.core;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String password;

  @Enumerated(value = EnumType.STRING)
  private UserRoleEnum role;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.role = UserRoleEnum.USER;
  }

}

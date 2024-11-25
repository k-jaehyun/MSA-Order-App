package com.spring_cloud.eureka.client.auth.core;

public enum UserRoleEnum {
  USER(Authority.USER),
  ADMIN(Authority.ADMIN);

  private final String authority;

  UserRoleEnum(String authority) {
    this.authority = authority;
  }

  public static class Authority {

    public static final String USER = "ROLE_CUSTOMER";
    public static final String ADMIN = "ROLE_ADMIN";
  }
}

package com.spring_cloud.eureka.client.order.core;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private List<Long> productIdList;

  private String username;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  public Order(List<Long> productIdList, String username) {
    this.productIdList = productIdList;
    this.username = username;
    this.orderStatus = OrderStatus.CREATED;
  }
}

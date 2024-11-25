package com.spring_cloud.eureka.client.order.core;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "order_product_ids", joinColumns = @JoinColumn(name = "order_id"))
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

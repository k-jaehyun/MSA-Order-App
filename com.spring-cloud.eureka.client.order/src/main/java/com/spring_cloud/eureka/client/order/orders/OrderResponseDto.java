package com.spring_cloud.eureka.client.order.orders;

import com.spring_cloud.eureka.client.order.core.Order;
import com.spring_cloud.eureka.client.order.core.OrderStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

  private List<Long> productIdList;

  private OrderStatus orderStatus;

  private String username;

  public OrderResponseDto(Order order) {
    this.productIdList = order.getProductIdList();
    this.orderStatus = order.getOrderStatus();
    this.username = order.getUsername();
  }

}

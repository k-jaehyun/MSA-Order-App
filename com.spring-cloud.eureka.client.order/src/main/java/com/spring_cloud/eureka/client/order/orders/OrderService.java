package com.spring_cloud.eureka.client.order.orders;

import com.spring_cloud.eureka.client.order.client.ProductClient;
import com.spring_cloud.eureka.client.order.core.Order;
import com.spring_cloud.eureka.client.order.core.OrderStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductClient productClient;


  public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, HttpHeaders headers) {

    String username = headers.getFirst("username");

    List<Long> productIdList = orderRequestDto.getProductIdList();

    for (Long productId : productIdList) {
      productClient.reduceQuantity(productId, headers);
    }

    Order order = new Order(productIdList, username);
    orderRepository.save(order);

    return new OrderResponseDto(productIdList, OrderStatus.CREATED, username);
  }



}

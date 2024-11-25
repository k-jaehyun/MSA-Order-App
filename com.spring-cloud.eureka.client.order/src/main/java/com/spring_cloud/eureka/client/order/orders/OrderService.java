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


  public OrderResponseDto getOrder(Long orderId, HttpHeaders headers) {

    String username = headers.getFirst("username");
    String role = headers.getFirst("Role");

    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 orderId 입니다."));

    if (!role.equals("ADMIN") && !order.getUsername().equals(username)) {
      throw new IllegalArgumentException("자신의 주문만 조회 할 수 있습니다.");
    }

    return new OrderResponseDto(order.getProductIdList(), order.getOrderStatus(), username);
  }
}

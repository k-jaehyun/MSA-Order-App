package com.spring_cloud.eureka.client.order.orders;

import com.spring_cloud.eureka.client.order.core.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

  Page<Order> searchOrders(Long productId, Pageable pageable, String username);
}

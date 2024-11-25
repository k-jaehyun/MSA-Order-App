package com.spring_cloud.eureka.client.order.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity createOrder(
      @RequestBody OrderRequestDto orderRequestDto,
      @RequestHeader HttpHeaders headers
  ) {

    OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto, headers);

    return ResponseEntity.ok(orderResponseDto);

  }

  @GetMapping("/{orderId}")
  public ResponseEntity getOrder(
      @PathVariable Long orderId,
      @RequestHeader HttpHeaders headers
  ) {

    OrderResponseDto orderResponseDto = orderService.getOrder(orderId, headers);

    return ResponseEntity.ok(orderResponseDto);
  }

  @GetMapping
  public ResponseEntity getOrders(
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "") Long productId,
      @RequestParam(defaultValue = "orderStatus") String sortBy,
      @RequestParam(defaultValue = "DESC") Direction direction,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestHeader HttpHeaders headers) {

    Page<OrderResponseDto> pagedOrderDto = orderService.getOrders(size, productId, sortBy,
        direction, page, headers);

    return ResponseEntity.ok(pagedOrderDto);
  }

}

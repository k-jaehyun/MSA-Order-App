package com.spring_cloud.eureka.client.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "product-service")
public interface ProductClient {

  @GetMapping("/products/{productId}/reduceQuantity")
  String reduceQuantity(
      @PathVariable Long productId,
      @RequestHeader HttpHeaders headers);

}

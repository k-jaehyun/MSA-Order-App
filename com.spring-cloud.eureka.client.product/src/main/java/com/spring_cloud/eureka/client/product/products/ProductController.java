package com.spring_cloud.eureka.client.product.products;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity createProduct(
      @RequestBody ProductDto productRequestDto,
      @RequestHeader HttpHeaders headers
  ) {

    ProductDto productResponseDto = productService.createProduct(productRequestDto, headers);

    return ResponseEntity.ok(productResponseDto);
  }

  @GetMapping("/{productId}")
  public ResponseEntity getProduct(@PathVariable Long productId) {

    ProductDto productResponseDto = productService.getProduct(productId);

    return ResponseEntity.ok(productResponseDto);
  }

}

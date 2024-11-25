package com.spring_cloud.eureka.client.product.products;

import com.spring_cloud.eureka.client.product.core.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

  private String name;

  private Long quantity;

  public ProductDto(Product product) {
    this.name = product.getName();
    this.quantity = product.getQuantity();
  }

  public static Product toEntity(ProductDto productRequestDto) {
    return Product.builder()
        .name(productRequestDto.getName())
        .quantity(productRequestDto.getQuantity())
        .build();
  }
}

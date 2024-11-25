package com.spring_cloud.eureka.client.product.products;

import com.spring_cloud.eureka.client.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;


  public ProductDto createProduct(ProductDto productRequestDto, HttpHeaders headers) {
    String username = headers.getFirst("username");
    String role = headers.getFirst("Role");
    String issuer = headers.getFirst("issuer");
    String issuedAt = headers.getFirst("issuedAt");

    if (!role.equals("ADMIN")) {
      throw new IllegalArgumentException("ADMIN만 접근 가능합니다.");
    }

    Product product = ProductDto.toEntity(productRequestDto);

    productRepository.save(product);

    return new ProductDto(product);
  }
}

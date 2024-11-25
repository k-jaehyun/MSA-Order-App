package com.spring_cloud.eureka.client.product.products;

import com.spring_cloud.eureka.client.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;


  @Transactional
  public ProductDto createProduct(ProductDto productRequestDto, HttpHeaders headers) {
    String username = headers.getFirst("username");
    String role = headers.getFirst("Role");
    String issuer = headers.getFirst("issuer");
    String issuedAt = headers.getFirst("issuedAt");

    if (!role.equals("ADMIN")) {
      throw new IllegalArgumentException("ADMIN만 접근 가능합니다.");
    }
    Product product = productRepository.findProductByName(productRequestDto.getName()).orElse(null);

    if (product == null) {
      Product newProduct = ProductDto.toEntity(productRequestDto);
      productRepository.save(newProduct);
      return new ProductDto(newProduct);
    }

    product.setQuantity(product.getQuantity() + productRequestDto.getQuantity());

    return new ProductDto(product);
  }

  public ProductDto getProduct(Long productId) {
    Product product = productRepository.findById(productId).orElse(null);
    return new ProductDto(product);
  }

  public Page<ProductDto> getPrducts(int size, String keyword, String sortBy, Direction direction,
      Integer page) {

    Pageable pageable = PageRequest.of(page, size, direction, sortBy);

    Page<Product> pagedProduct = productRepository.searchProducts(keyword, pageable);

    return pagedProduct.map(ProductDto::new);
  }
}

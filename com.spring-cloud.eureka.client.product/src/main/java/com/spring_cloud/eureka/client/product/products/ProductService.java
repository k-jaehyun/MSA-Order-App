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


  public ProductDto createProduct(ProductDto productRequestDto, HttpHeaders headers) {
    String username = headers.getFirst("username");
    String role = headers.getFirst("Role");
    String issuer = headers.getFirst("issuer");
    String issuedAt = headers.getFirst("issuedAt");

    if (!role.equals("ADMIN")) {
      throw new IllegalArgumentException("ADMIN만 접근 가능합니다.");
    }

    if (productRepository.findProductByName(productRequestDto.getName()).isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 product 입니다.");
    }

    Product product = ProductDto.toEntity(productRequestDto);
    productRepository.save(product);

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

  @Transactional
  public ProductDto updateProduct(Long productId, ProductDto productRequestDto,
      HttpHeaders headers) {

    if (!headers.getFirst("Role").equals("ADMIN")) {
      throw new IllegalArgumentException("ADMIN만 접근 가능합니다.");
    }

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 productId 입니다."));

    product.setName(productRequestDto.getName());
    product.setQuantity(productRequestDto.getQuantity());

    return new ProductDto(product);
  }

  @Transactional
  public void deleteProduct(Long productId, HttpHeaders headers) {

    if (!headers.getFirst("Role").equals("ADMIN")) {
      throw new IllegalArgumentException("ADMIN만 접근 가능합니다.");
    }

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 productId 입니다."));

    product.delete();

  }

  @Transactional
  public ProductDto reduceProductQuantity(Long productId, HttpHeaders headers) {

    if (!headers.getFirst("Role").equals("ADMIN")) {
      throw new IllegalArgumentException("ADMIN만 접근 가능합니다.");
    }

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 productId 입니다."));

    if(product.getQuantity()==0) {
      throw new IllegalArgumentException("상품이 0개 입니다.");
    }

    product.setQuantity(product.getQuantity()-1);

    return new ProductDto(product);
  }
}

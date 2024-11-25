package com.spring_cloud.eureka.client.product.products;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping
  public ResponseEntity searchProducts(
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "") String keyword,
      @RequestParam(defaultValue = "name") String sortBy,
      @RequestParam(defaultValue = "DESC") Direction direction,
      @RequestParam(defaultValue = "0") Integer page) {

    Page<ProductDto> pagedProductDto = productService.getPrducts(size, keyword, sortBy, direction,
        page);

    return ResponseEntity.ok(pagedProductDto);
  }

  @PutMapping("/{productId}")
  public ResponseEntity updateProduct(
      @PathVariable Long productId,
      @RequestBody ProductDto productRequestDto,
      @RequestHeader HttpHeaders headers) {

    ProductDto productResponseDto = productService.updateProduct(productId, productRequestDto,
        headers);

    return ResponseEntity.ok(productResponseDto);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity deleteProduct(
      @PathVariable Long productId,
      @RequestHeader HttpHeaders headers) {

    productService.deleteProduct(productId, headers);

    return ResponseEntity.ok("상품 삭제 성공");
  }

}

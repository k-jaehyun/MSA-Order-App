package com.spring_cloud.eureka.client.product.products;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring_cloud.eureka.client.product.core.Product;
import com.spring_cloud.eureka.client.product.core.QProduct;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ProductRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Product> searchProducts(String keyword, Pageable pageable) {

    QProduct product = QProduct.product;

    List<Product> result = queryFactory
        .selectFrom(product)
        .where(product.name.likeIgnoreCase("%" + keyword + "%"))
        .orderBy(
            getDynamicSort(pageable.getSort(), product.getType(), product.getMetadata())
                .toArray(OrderSpecifier[]::new))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(product.count())
        .from(product)
        .where(product.name.likeIgnoreCase("%" + keyword + "%"))
        .fetchOne();

    if (total == null) {
      total = 0L;
    }

    return new PageImpl<>(result, pageable, total);
  }

  public static <T> List<OrderSpecifier> getDynamicSort(Sort sort, Class<? extends T> entityClass,
      PathMetadata pathMetadata) {
    List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

    // 도메인 클래스에 맞는 PathBuilder를 생성
    PathBuilder<Object> pathBuilder = new PathBuilder<>(entityClass, pathMetadata);

    sort.stream().forEach(orderSpecifier -> {
      Order direction = orderSpecifier.isAscending() ? Order.ASC : Order.DESC;
      String prop = orderSpecifier.getProperty();

      // 동적으로 해당 필드에 접근
      orderSpecifiers.add(new OrderSpecifier(direction, pathBuilder.get(prop)));
    });

    return orderSpecifiers;
  }

}

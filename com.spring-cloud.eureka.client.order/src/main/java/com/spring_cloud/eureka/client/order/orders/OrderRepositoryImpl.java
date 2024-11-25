package com.spring_cloud.eureka.client.order.orders;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring_cloud.eureka.client.order.core.Order;
import com.spring_cloud.eureka.client.order.core.QOrder;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public OrderRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Order> searchOrders(Long productId, Pageable pageable, String username) {
    QOrder order = QOrder.order;

    BooleanExpression searchCondition = order.username.eq(username);

    if (productId != null) {
      searchCondition = searchCondition.and(order.productIdList.contains(productId));
    }

    List<Order> result = queryFactory
        .selectFrom(order)
        .where(searchCondition)
        .orderBy(
            getDynamicSort(pageable.getSort(), order.getType(), order.getMetadata())
                .toArray(OrderSpecifier[]::new))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(order.count())
        .where(searchCondition)
        .from(order)
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
      com.querydsl.core.types.Order direction =
          orderSpecifier.isAscending() ? com.querydsl.core.types.Order.ASC
              : com.querydsl.core.types.Order.DESC;
      String prop = orderSpecifier.getProperty();

      // 동적으로 해당 필드에 접근
      orderSpecifiers.add(new OrderSpecifier(direction, pathBuilder.get(prop)));
    });

    return orderSpecifiers;
  }
}

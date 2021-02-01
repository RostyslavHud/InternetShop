package com.internetshop.mysqlRepository;

import com.internetshop.mysqlModel.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByOrderNumber(Long orderNumber);

    Page<Order> findAllByUserId(Long userId, Pageable pageable);

    @Query("select max(o.orderNumber) from Order o")
    Long findMaxOrderNumber();
}

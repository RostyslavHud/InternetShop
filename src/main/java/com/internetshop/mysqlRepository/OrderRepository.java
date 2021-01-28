package com.internetshop.mysqlRepository;

import com.internetshop.mysqlModel.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findOrderByOrderNumber(Long orderNumber);

    List<Order> findAllByUserId(Long userId);

    @Query("select max(o.orderNumber) from Order o")
    Long findMaxOrderNumber();
}

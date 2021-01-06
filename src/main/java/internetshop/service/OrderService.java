package internetshop.service;

import internetshop.model.Order;

import java.util.List;

public interface OrderService {
    Order getById(Long id);
    List<Order> getAllByUserId(Long id);
    Order getByNumber(Long orderNumber);
    void add(Order order);
    void update(Order order);
    void delete(Order order);
}

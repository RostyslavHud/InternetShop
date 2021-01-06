package internetshop.service.implementation;

import internetshop.model.Order;
import internetshop.repository.OrderRepository;
import internetshop.repository.ProductRepository;
import internetshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Order getById(Long id) {
        return orderRepository.addProductsToOrder(orderRepository.findById(id).get());
    }

    @Override
    public List<Order> getAllByUserId(Long id) {
        List<Order> orders = (List<Order>) orderRepository.findAllById(Collections.singleton(id));
        return orders;
    }

    @Override
    public Order getByNumber(Long orderNumber) {
        Optional<Order> order = orderRepository.findOrderByOrderNumber(orderNumber);
        return order.get();
    }

    @Override
    public void add(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }
}
package internetshop.service.implementation;

import internetshop.enums.Role;
import internetshop.model.Order;
import internetshop.enums.OrderStatus;
import internetshop.model.User;
import internetshop.repository.OrderRepository;
import internetshop.repository.ProductRepository;
import internetshop.repository.UserRepository;
import internetshop.service.OrderService;
import internetshop.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("orderService")
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Order getById(Long id) throws ServiceException {
        if (id < 1) {
            throw new ServiceException("Id must be below zero");
        }
        return orderRepository.findById(id).orElseThrow(() -> new ServiceException("Order not found"));
    }

    @Override
    public List<Order> getAll(String userName) throws ServiceException {
        if (userName.equals("")) {
            throw new ServiceException("User name can't be empty");
        }
        List<Order> orders = new ArrayList<>();
        User user = userRepository.findByName(userName).orElseThrow(() -> new ServiceException("User not found"));
        if (user.getRole() == Role.USER){
            orders = orderRepository.findAllByUserId(user.getId());
        }else if (user.getRole() == Role.ADMIN){
            log.error("Test1");
            orderRepository.findAll().forEach(orders::add);
            log.error("Test2");
        }
        return orders;
    }

    @Override
    public Order getByNumber(Long orderNumber) throws ServiceException {
        if (orderNumber == 0) {
            throw new ServiceException("Order number can't be 0");
        }
        Optional<Order> order = orderRepository.findOrderByOrderNumber(orderNumber);
        return order.orElseThrow();
    }

    @Override
    public void add(Order order, String userName) throws ServiceException {
        if (order == null){
            throw new ServiceException("Order can't be null");
        }
        Long maxOrderNumber = orderRepository.findMaxOrderNumber();
        order.setOrderNumber(maxOrderNumber + 1);
        order.setUser(userRepository.findByName(userName).orElseThrow(() -> new ServiceException("User not found")));
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        orderRepository.save(order);
    }

    @Override
    public void update(Order order, String userName) throws ServiceException {
        if (order == null){
            throw new ServiceException("Order can't be null");
        }
        if (order.getOrderNumber() < 1){
            throw new ServiceException("Order id must be below zero");
        }
        Order savedOrder = orderRepository.findOrderByOrderNumber(order.getOrderNumber())
                                          .orElseThrow(() -> new ServiceException("Order not found"));
        if (order.getOrderItems().get(0).getProduct().getId() != 0){
            savedOrder.getOrderItems().get(0).setProduct(order.getOrderItems().get(0).getProduct());
        }
        if (order.getOrderItems().get(0).getProductQty() != 0){
            savedOrder.getOrderItems().get(0).setProductQty(order.getOrderItems().get(0).getProductQty());
        }

        savedOrder.setUpdated(userName);
        savedOrder.setShippingAddress(order.getShippingAddress());
        savedOrder.setDescription(order.getDescription());

        orderRepository.save(savedOrder);
    }

    @Override
    public void delete(Order order) throws ServiceException {
        if (order == null){
            throw new ServiceException("Order can't be null");
        }
        if (order.getId() < 1){
            throw new ServiceException("Order id must be below zero");
        }
        orderRepository.delete(order);
    }
}
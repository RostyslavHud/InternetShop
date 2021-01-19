package com.internetshop.service.implementation;

import com.internetshop.enums.Errors;
import com.internetshop.enums.Role;
import com.internetshop.model.Order;
import com.internetshop.enums.OrderStatus;
import com.internetshop.model.OrderItem;
import com.internetshop.model.User;
import com.internetshop.repository.OrderRepository;
import com.internetshop.repository.ProductRepository;
import com.internetshop.repository.UserRepository;
import com.internetshop.service.OrderService;
import com.internetshop.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            throw new ServiceException(Errors.INCORRECT_ID);
        }
        return orderRepository.findById(id).orElseThrow(() -> new ServiceException(Errors.ORDER_NOT_FOUND));
    }

    @Override
    public List<Order> getAll(String userName) throws ServiceException {
        if (userName.equals("")) {
            throw new ServiceException(Errors.EMPTY_USER_NAME);
        }
        List<Order> orders = new ArrayList<>();
        User user = userRepository.findByName(userName);
        if (user.getRole() == Role.USER) {
            orders = orderRepository.findAllByUserId(user.getId());
        } else if (user.getRole() == Role.ADMIN) {
            orderRepository.findAll().forEach(orders::add);
        }
        return orders;
    }

    @Override
    public void add(Order order, String userName) throws ServiceException {
        if (order == null) {
            throw new ServiceException(Errors.EMPTY_ORDER);
        }
        Long maxOrderNumber = orderRepository.findMaxOrderNumber();
        if (maxOrderNumber == null) {
            maxOrderNumber = 100000L;
        }
        order.setOrderNumber(maxOrderNumber + 1);
        order.setUser(userRepository.findByName(userName));
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        double price = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            price += orderItem.getProductQty() * productRepository.findById(orderItem.getProduct().getId())
                              .orElseThrow(() -> new ServiceException(Errors.PRODUCT_NOT_FOUND)).getPrice();
        }
        order.setPrice(price);
        orderRepository.save(order);
    }

    @Override
    public void update(Order order, String userName) throws ServiceException {
        if (order == null) {
            throw new ServiceException(Errors.EMPTY_ORDER);
        }
        if (order.getOrderNumber() < 1) {
            throw new ServiceException(Errors.INCORRECT_ID);
        }
        Order savedOrder = orderRepository.findOrderByOrderNumber(order.getOrderNumber())
                .orElseThrow(() -> new ServiceException(Errors.ORDER_NOT_FOUND));

        if (!order.getOrderItems().isEmpty()) {
            savedOrder.setOrderItems(order.getOrderItems());
            double price = 0;
            for (OrderItem orderItem : savedOrder.getOrderItems()) {
                price += orderItem.getProductQty() * productRepository.findById(orderItem.getProduct().getId())
                        .orElseThrow(() -> new ServiceException(Errors.PRODUCT_NOT_FOUND)).getPrice();
            }
            savedOrder.setPrice(price);
        }
        savedOrder.setUpdated(userName);
        savedOrder.setShippingAddress(order.getShippingAddress());
        savedOrder.setDescription(order.getDescription());

        orderRepository.save(savedOrder);
    }

    @Override
    public void delete(Order order) throws ServiceException {
        if (order == null) {
            throw new ServiceException(Errors.EMPTY_ORDER);
        }
        if (order.getId() < 1) {
            throw new ServiceException(Errors.INCORRECT_ID);
        }
        orderRepository.delete(order);
    }
}
package com.internetshop.service.implementation;

import com.internetshop.enums.Errors;
import com.internetshop.enums.Role;
import com.internetshop.mysqlModel.Order;
import com.internetshop.enums.OrderStatus;
import com.internetshop.mysqlModel.OrderItem;
import com.internetshop.mysqlModel.User;
import com.internetshop.mysqlRepository.OrderRepository;
import com.internetshop.mongoRepository.ProductRepository;
import com.internetshop.mysqlRepository.UserRepository;
import com.internetshop.service.OrderService;
import com.internetshop.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("orderService")
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
        Order order = orderRepository.findById(id).orElseThrow(() -> new ServiceException(Errors.ORDER_NOT_FOUND));
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setProduct(productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new ServiceException(Errors.PRODUCT_NOT_FOUND)));
        }
        return order;
    }

    @Override
    public Page<Order> getAll(String userName, Pageable pageable) throws ServiceException {
        if (userName.equals("")) {
            throw new ServiceException(Errors.EMPTY_USER_NAME);
        }
        Page<Order> orders = null;
        User user = userRepository.findByName(userName);
        if (user.getRole() == Role.USER) {
            orders = orderRepository.findAllByUserId(user.getId(), pageable);
        } else if (user.getRole() == Role.ADMIN) {
            orders = orderRepository.findAll(pageable);
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
            price += orderItem.getProductQty() * productRepository.findById(orderItem.getProductId())
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
                price += orderItem.getProductQty() * productRepository.findById(orderItem.getProductId())
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
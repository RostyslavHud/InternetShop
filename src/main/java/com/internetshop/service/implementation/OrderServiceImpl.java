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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
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
    @Cacheable(value = "order")
    public Order getById(Long id) throws ServiceException {
        if (id < 1) {
            throw new ServiceException(Errors.INCORRECT_ID);
        }
        Order order = orderRepository.findById(id).orElseThrow(() -> new ServiceException(Errors.ORDER_NOT_FOUND));
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setProduct(productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new ServiceException(Errors.PRODUCT_NOT_FOUND)));
        }
        log.info("Get order : {}", order.getOrderNumber());
        return order;
    }

    @Override
    @Cacheable("order")
    public Page<Order> getAll(String userName, Pageable pageable) throws ServiceException {
        if (userName.isBlank()) {
            throw new ServiceException(Errors.EMPTY_USER_NAME);
        }

        User user = userRepository.findByName(userName);
        if (user.getRole() == Role.USER) {
            return orderRepository.findAllByUserId(user.getId(), pageable);
        } else if (user.getRole() == Role.ADMIN) {
            return orderRepository.findAll(pageable);
        }
        log.info("Get {} orders on {} page", pageable.getPageSize(), pageable.getPageNumber());
        return null;
    }

    @Override
    @CacheEvict(value = "order", allEntries = true)
    public void add(Order order, String userName) throws ServiceException {

        Order checkedOrder = Optional.of(order).orElseThrow(() -> new ServiceException(Errors.EMPTY_ORDER));

        Long maxOrderNumber = orderRepository.findMaxOrderNumber();

        if (Objects.isNull(maxOrderNumber)) {
            maxOrderNumber = 100000L;
        }
        checkedOrder.setOrderNumber(maxOrderNumber + 1);
        checkedOrder.setUser(userRepository.findByName(userName));
        checkedOrder.setDate(LocalDateTime.now());
        checkedOrder.setStatus(OrderStatus.CREATED);
        double price = 0;
        for (OrderItem orderItem : checkedOrder.getOrderItems()) {
            price += orderItem.getProductQty() * productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new ServiceException(Errors.PRODUCT_NOT_FOUND)).getPrice();
        }
        checkedOrder.setPrice(price);
        log.info("Add order : {}", checkedOrder.getOrderNumber());
        orderRepository.save(checkedOrder);
    }

    @Override
    @CacheEvict(value = "order", allEntries = true)
    public void update(Order order, String userName) throws ServiceException {

        Order checkedOrder = Optional.of(order).orElseThrow(() -> new ServiceException(Errors.EMPTY_ORDER));

        if (checkedOrder.getOrderNumber() < 1) {
            throw new ServiceException(Errors.INCORRECT_ID);
        }
        Order savedOrder = orderRepository.findOrderByOrderNumber(checkedOrder.getOrderNumber())
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

        log.info("Update order {}: ", order.getOrderNumber());
        orderRepository.save(savedOrder);
    }

    @Override
    @CacheEvict(value = "order", allEntries = true)
    public void delete(Order order) throws ServiceException {

        Order checkedOrder = Optional.of(order).orElseThrow(() -> new ServiceException(Errors.EMPTY_ORDER));

        if (checkedOrder.getId() < 1) {
            throw new ServiceException(Errors.INCORRECT_ID);
        }
        log.info("Delete order {}: ", checkedOrder.getOrderNumber());
        orderRepository.delete(checkedOrder);
    }
}
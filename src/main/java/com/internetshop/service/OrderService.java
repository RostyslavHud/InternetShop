package com.internetshop.service;

import com.internetshop.exception.ServiceException;
import com.internetshop.mysqlModel.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Order getById(Long id) throws ServiceException;

    List<Order> getAll(String userName) throws ServiceException;

    void add(Order order, String userName) throws ServiceException;

    void update(Order order, String userName) throws ServiceException;

    void delete(Order order) throws ServiceException;
}

package com.internetshop.service;

import com.internetshop.exception.ServiceException;
import com.internetshop.mysqlModel.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order getById(Long id) throws ServiceException;

    Page<Order> getAll(String userName, Pageable pageable) throws ServiceException;

    void add(Order order, String userName) throws ServiceException;

    void update(Order order, String userName) throws ServiceException;

    void delete(Order order) throws ServiceException;
}

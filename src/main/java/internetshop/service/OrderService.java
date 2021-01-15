package internetshop.service;

import internetshop.exception.ServiceException;
import internetshop.model.Order;

import java.util.List;

public interface OrderService {
    Order getById(Long id) throws ServiceException;
    List<Order> getAll(String userName) throws ServiceException;
    Order getByNumber(Long orderNumber) throws ServiceException;
    void add(Order order, String userName) throws ServiceException;
    void update(Order order, String userName) throws ServiceException;
    void delete(Order order) throws ServiceException;
}

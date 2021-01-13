package internetshop.service;

import internetshop.exception.ServiceException;
import internetshop.model.Order;
import internetshop.exception.RepositoryException;

import java.util.List;

public interface OrderService {
    Order getById(Long id) throws RepositoryException, ServiceException;
    List<Order> getAll(String userName) throws ServiceException, RepositoryException;
    Order getByNumber(Long orderNumber) throws ServiceException;
    void add(Order order, String userName) throws ServiceException;
    void update(Order order, String userName) throws ServiceException;
    void delete(Order order) throws ServiceException;
}

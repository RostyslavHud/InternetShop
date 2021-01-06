package internetshop.service;

import internetshop.model.Order;
import internetshop.repository.RepositoryException;

import java.util.List;

public interface OrderService {
    Order getById(Long id) throws RepositoryException, ServiceException;
    List<Order> getAllByUserId(Long id) throws ServiceException;
    Order getByNumber(Long orderNumber) throws ServiceException;
    void add(Order order) throws ServiceException;
    void update(Order order) throws ServiceException;
    void delete(Order order) throws ServiceException;
}

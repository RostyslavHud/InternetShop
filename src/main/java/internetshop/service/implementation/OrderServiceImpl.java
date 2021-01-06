package internetshop.service.implementation;

import internetshop.model.Order;
import internetshop.repository.OrderRepository;
import internetshop.repository.ProductRepository;
import internetshop.repository.RepositoryException;
import internetshop.service.OrderService;
import internetshop.service.ServiceException;
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
    public Order getById(Long id) throws RepositoryException, ServiceException {
        if (id < 1) {
            throw new ServiceException("Id must be below zero");
        }
        return orderRepository.addProductsToOrder(orderRepository.findById(id).get());
    }

    @Override
    public List<Order> getAllByUserId(Long id) throws ServiceException {
        if (id < 1) {
            throw new ServiceException("Id must be below zero");
        }
        List<Order> orders = (List<Order>) orderRepository.findAllById(Collections.singleton(id));
        return orders;
    }

    @Override
    public Order getByNumber(Long orderNumber) throws ServiceException {
        if (orderNumber == 0) {
            throw new ServiceException("Order number can't be 0");
        }
        Optional<Order> order = orderRepository.findOrderByOrderNumber(orderNumber);
        return order.get();
    }

    @Override
    public void add(Order order) throws ServiceException {
        if (order == null){
            throw new ServiceException("Order can't be null");
        }
        if (order.getId() < 1){
            throw new ServiceException("Order id must be below zero");
        }
        orderRepository.save(order);
    }

    @Override
    public void update(Order order) throws ServiceException {
        if (order == null){
            throw new ServiceException("Order can't be null");
        }
        if (order.getId() < 1){
            throw new ServiceException("Order id must be below zero");
        }
        orderRepository.save(order);
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
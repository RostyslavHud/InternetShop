package internetshop.service.implementation;

import internetshop.model.Order;
import internetshop.model.OrderItem;
import internetshop.enums.OrderStatus;
import internetshop.repository.OrderRepository;
import internetshop.repository.ProductRepository;
import internetshop.exception.RepositoryException;
import internetshop.repository.UserRepository;
import internetshop.service.OrderService;
import internetshop.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Order getById(Long id) throws RepositoryException, ServiceException {
        if (id < 1) {
            throw new ServiceException("Id must be below zero");
        }
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> getAllByUserId(Long id) throws ServiceException, RepositoryException {
        if (id < 1) {
            throw new ServiceException("Id must be below zero");
        }
        List<Order> orders = orderRepository.findAllByUserId(id).get();
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
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public void add(Order order, String userName) throws ServiceException {
        if (order == null){
            throw new ServiceException("Order can't be null");
        }
        List<Order> orders = (List<Order>) orderRepository.findAll();
        Long maxOrderNumber = orders.get(orders.size() - 1).getOrderNumber();
        Order newOrder = order;
        newOrder.setOrderNumber(maxOrderNumber + 1);
        newOrder.setUser(userRepository.findByName(userName).get());
        newOrder.setDate(LocalDateTime.now());
        newOrder.setStatus(OrderStatus.CREATED);
        for (OrderItem orderItem : newOrder.getOrderItems()){
            orderItem.setProduct(productRepository.findByName(orderItem.getProduct().getName()).get());
        }
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
        Order savedOrder = orderRepository.findOrderByOrderNumber(order.getOrderNumber()).get();
        if (!order.getOrderItems().get(0).getProduct().getName().equals("None")){
            savedOrder.getOrderItems().get(0).setProduct(productRepository.findByName(order.getOrderItems().get(0).getProduct().getName()).get());
        }
        if (order.getOrderItems().get(0).getProductQty() != 0){
            savedOrder.getOrderItems().get(0).setProductQty(order.getOrderItems().get(0).getProductQty());
        }

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
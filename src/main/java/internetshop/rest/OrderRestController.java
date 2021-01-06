package internetshop.rest;

import internetshop.model.Order;
import internetshop.model.User;
import internetshop.repository.RepositoryException;
import internetshop.service.OrderService;
import internetshop.service.ServiceException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders(@AuthenticationPrincipal User user) throws ServiceException {
        return orderService.getAllByUserId(user.getId());
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) throws ServiceException, RepositoryException {
        return orderService.getById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) throws ServiceException {
        orderService.add(order);
        return orderService.getByNumber(order.getOrderNumber());
    }

    @PutMapping("/{orderNumber}")
    public Order updateOrder(@PathVariable Long orderNumber, @RequestBody Order order) throws ServiceException {
        orderService.update(order);
        return orderService.getByNumber(orderNumber);
    }

    @DeleteMapping("/{orderNumber}")
    public void deleteOrder(@PathVariable Long orderNumber) throws ServiceException {
        orderService.delete(orderService.getByNumber(orderNumber));
    }
}
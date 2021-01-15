package internetshop.rest;

import internetshop.model.Order;
import internetshop.service.OrderService;
import internetshop.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@Slf4j
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders(@AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        return orderService.getAll(userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) throws ServiceException {
        return orderService.getById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order, @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        orderService.add(order, userDetails.getUsername());
        return order;
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order, @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        orderService.update(order, userDetails.getUsername());
        return order;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) throws ServiceException {
        orderService.delete(orderService.getById(id));
    }
}
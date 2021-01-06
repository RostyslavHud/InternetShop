package internetshop.rest;

import internetshop.model.Order;
import internetshop.model.User;
import internetshop.service.OrderService;
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
    public List<Order> getOrders(@AuthenticationPrincipal User user){
        List<Order> orders = orderService.getAllByUserId(user.getId());
        return orders;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id){
        Order order = orderService.getById(id);
        return order;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order){
        orderService.add(order);
        Order result = orderService.getByNumber(order.getOrderNumber());
        return  result;
    }

    @PutMapping("/{orderNumber}")
    public Order updateOrder(@PathVariable Long orderNumber, @RequestBody Order order){
        orderService.update(order);
        Order result = orderService.getByNumber(orderNumber);
        return result;
    }

    @DeleteMapping("/{orderNumber}")
    public void deleteOrder(@PathVariable Long orderNumber){
        orderService.delete(orderService.getByNumber(orderNumber));
    }

}

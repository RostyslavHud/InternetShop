package internetshop.rest;

import internetshop.model.Order;
import internetshop.model.User;
import internetshop.exception.RepositoryException;
import internetshop.service.OrderService;
import internetshop.service.ProductService;
import internetshop.exception.ServiceException;
import internetshop.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderRestController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    public OrderRestController(OrderService orderService, UserService userService, ProductService productService){
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public List<Order> getOrders(@AuthenticationPrincipal UserDetails userDetails) throws ServiceException, RepositoryException {
        User user = userService.findByName(userDetails.getUsername()).get();
        return orderService.getAllByUserId(user.getId());
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) throws ServiceException, RepositoryException {
        return orderService.getById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order, @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        orderService.add(order, userDetails.getUsername());
        return orderService.getByNumber(order.getOrderNumber());
    }

    @PutMapping
    public Order updateOrder(@RequestBody Order order, @AuthenticationPrincipal UserDetails userDetails) throws ServiceException, RepositoryException {
        orderService.update(order, userDetails.getUsername());
        return orderService.getByNumber(order.getOrderNumber());
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) throws ServiceException, RepositoryException {
        orderService.delete(orderService.getById(id));
    }
}
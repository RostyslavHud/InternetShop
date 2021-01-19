package com.internetshop.rest;

import com.internetshop.dto.CreationOrderDTO;
import com.internetshop.dto.SimpleOrderDTO;
import com.internetshop.dto.UpdatedOrderDTO;
import com.internetshop.mapper.OrderMapper;
import com.internetshop.model.Order;
import com.internetshop.service.OrderService;
import com.internetshop.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@Slf4j
public class OrderRestController {

    private final OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<SimpleOrderDTO> getOrders(@AuthenticationPrincipal UserDetails userDetails) throws ServiceException {

        return orderMapper.orderToSimpleOrder(orderService.getAll(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public SimpleOrderDTO getOrder(@PathVariable Long id) throws ServiceException {
        return orderMapper.orderToSimpleOrder(orderService.getById(id));
    }

    @PostMapping
    public CreationOrderDTO createOrder(@Valid @RequestBody CreationOrderDTO creationOrderDTO,
                                        @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {

        Order order = orderMapper.creationOrderToOrder(creationOrderDTO);
        orderService.add(order, userDetails.getUsername());
        return creationOrderDTO;
    }

    @PutMapping("/{id}")
    public UpdatedOrderDTO updateOrder(@PathVariable Long id, @RequestBody UpdatedOrderDTO updatedOrderDTO,
                                       @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {

        orderService.update(orderMapper.updatedOrderToOrder(updatedOrderDTO), userDetails.getUsername());
        return updatedOrderDTO;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) throws ServiceException {
        orderService.delete(orderService.getById(id));
    }
}
package com.internetshop.rest;

import com.internetshop.dto.CreationOrderDTO;
import com.internetshop.dto.SimpleOrderDTO;
import com.internetshop.dto.UpdatedOrderDTO;
import com.internetshop.mapper.OrderMapper;
import com.internetshop.mongoModel.Product;
import com.internetshop.mysqlModel.Order;
import com.internetshop.service.OrderService;
import com.internetshop.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderRestController {

    private final OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "Get all orders for customer")
    @ApiResponse(responseCode = "200", description = "Get all orders for customer. " +
                            "Simple customers see just their own orders. Admin see all orders",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleOrderDTO.class))})
    public List<SimpleOrderDTO> getOrders(@Parameter(description = "Authorized customer") @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        return orderMapper.orderToSimpleOrder(orderService.getAll(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by id")
    @ApiResponse(responseCode = "200", description = "Get order by id",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleOrderDTO.class))})
    public SimpleOrderDTO getOrder(@Parameter(description = "Order id for get") @PathVariable Long id) throws ServiceException {
        return orderMapper.orderToSimpleOrder(orderService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Add new order")
    @ApiResponse(responseCode = "200", description = "Add new order",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CreationOrderDTO.class))})
    public CreationOrderDTO createOrder(@Parameter(description = "New order") @Valid @RequestBody CreationOrderDTO creationOrderDTO,
                                        @Parameter(description = "Authorized customer") @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {

        Order order = orderMapper.creationOrderToOrder(creationOrderDTO);
        orderService.add(order, userDetails.getUsername());
        return creationOrderDTO;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order by id")
    @ApiResponse(responseCode = "200", description = "Update order by id",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UpdatedOrderDTO.class))})
    public UpdatedOrderDTO updateOrder(@Parameter(description = "Order id which have to updated") @PathVariable Long id,
                                       @Parameter(description = "Order for update") @Valid @RequestBody UpdatedOrderDTO updatedOrderDTO,
                                       @Parameter(description = "Authorized customer") @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {

        orderService.update(orderMapper.updatedOrderToOrder(updatedOrderDTO), userDetails.getUsername());
        return updatedOrderDTO;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order by id")
    @ApiResponse(responseCode = "200", description = "Delete order by id",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))})
    public void deleteOrder(@Parameter(description = "Order id which have to be deleted") @PathVariable Long id) throws ServiceException {
        orderService.delete(orderService.getById(id));
    }
}
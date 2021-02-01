package com.internetshop.rest;

import com.internetshop.dto.*;
import com.internetshop.enums.OrderStatus;
import com.internetshop.mapper.OrderMapper;
import com.internetshop.mysqlModel.Order;
import com.internetshop.mysqlModel.OrderItem;
import com.internetshop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderRestController.class)
class OrderRestControllerMvcTest extends AbstractRestControllerMvcTest {

    @MockBean
    OrderService orderService;

    @MockBean
    OrderMapper orderMapper;

    Page<Order> orders = new PageImpl<>(asList(new Order(), new Order()), PageRequest.of(0, 2), 2);

    @Test
    void getAllOrders() throws Exception {
        when(orderService.getAll(any(), any())).thenReturn(orders);
        when(orderMapper.orderToSimpleOrder(orders.getContent())).thenReturn(asList(
                new SimpleOrderDTO(1L, 123123123L, LocalDateTime.now(),
                        OrderStatus.CREATED, new SimpleUserDTO(),
                        asList(new OrderItem(), new OrderItem()), "ship", "desc", 2.2,
                        "admin"), new SimpleOrderDTO()));

        mockMvc.perform(get("/v1/orders")).andExpect(status().isOk());
    }

    @Test
    void getOrder() throws Exception {
        when(orderService.getById(1L)).thenReturn(new Order());
        when(orderMapper.orderToSimpleOrder(new Order())).thenReturn(new SimpleOrderDTO());

        mockMvc.perform(get("/v1/orders/{id}", 1L)).andExpect(status().isOk());
    }

    @Test
    void addOrder() throws Exception {
        String expectedResult = "{\"orderItems\":[{\"id\":null,\"productId\":null,\"productQty\":0,\"product\":null}," +
                "{\"id\":null,\"productId\":null,\"productQty\":0,\"product\":null}],\"shippingAddress\":\"ship\"," +
                "\"description\":\"desc\",\"orderItemsEmpty\":false}";

        when(orderMapper.creationOrderToOrder(new CreationOrderDTO())).thenReturn(new Order());
        doNothing().when(orderService).add(new Order(), "user");

        mockMvc.perform(post("/v1/orders")
                .content(asJsonString(new CreationOrderDTO(asList(new OrderItem(), new OrderItem()),
                        "ship", "desc")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void addOrderWithoutProductError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"orderItemsEmpty\":\"You nothing buy\"}}";

        when(orderMapper.creationOrderToOrder(new CreationOrderDTO())).thenReturn(new Order());
        doNothing().when(orderService).add(new Order(), "user");

        mockMvc.perform(post("/v1/orders")
                .content(asJsonString(new CreationOrderDTO(asList(), "ship", "desc")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void addOrderWithoutShippingError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"shippingAddress\":\"Shipping address is mandatory\"}}";

        when(orderMapper.creationOrderToOrder(new CreationOrderDTO())).thenReturn(new Order());
        doNothing().when(orderService).add(new Order(), "user");

        mockMvc.perform(post("/v1/orders")
                .content(asJsonString(new CreationOrderDTO(asList(new OrderItem(), new OrderItem()),
                        "", "desc")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void updateOrder() throws Exception {
        String expectedResult = "{\"orderNumber\":123123123,\"orderItems\":[{\"id\":null,\"productId\":null," +
                "\"productQty\":0,\"product\":null},{\"id\":null,\"productId\":null,\"productQty\":0," +
                "\"product\":null}],\"shippingAddress\":\"ship\",\"description\":\"desc\"}";

        when(orderMapper.updatedOrderToOrder(new UpdatedOrderDTO())).thenReturn(new Order());
        doNothing().when(orderService).update(new Order(), "user");

        mockMvc.perform(put("/v1/orders/{id}", 1L)
                .content(asJsonString(new UpdatedOrderDTO(123123123L,
                        asList(new OrderItem(), new OrderItem()), "ship", "desc")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void updateOrderWithoutShippingError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"shippingAddress\":\"Shipping address is mandatory\"}}";

        when(orderMapper.updatedOrderToOrder(new UpdatedOrderDTO())).thenReturn(new Order());
        doNothing().when(orderService).update(new Order(), "user");

        mockMvc.perform(put("/v1/orders/{id}", 1L)
                .content(asJsonString(new UpdatedOrderDTO(123123123L,
                        asList(new OrderItem(), new OrderItem()), "", "desc")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void deleteOrder() throws Exception {
        when(orderService.getById(1L)).thenReturn(new Order());
        doNothing().when(orderService).delete(new Order());

        mockMvc.perform(delete("/v1/orders/{id}", 1L)).andExpect(status().isOk());
    }
}

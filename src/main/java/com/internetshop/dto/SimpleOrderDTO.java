package com.internetshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.internetshop.enums.OrderStatus;
import com.internetshop.model.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class SimpleOrderDTO {

    private Long id;

    private Long orderNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    private OrderStatus status;

    private SimpleUserDTO user;

    private List<OrderItem> orderItems;

    private String shippingAddress;

    private String description;

    private double price;

    private String updated;
}
package com.internetshop.mapper;

import com.internetshop.dto.CreationOrderDTO;
import com.internetshop.dto.SimpleOrderDTO;
import com.internetshop.dto.UpdatedOrderDTO;
import com.internetshop.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "orderItems", source = "order.orderItems"),
            @Mapping(target = "shippingAddress", source = "order.shippingAddress"),
            @Mapping(target = "description", source = "order.description")
    })
    Order creationOrderToOrder(CreationOrderDTO order);

    @Mappings({
            @Mapping(target = "id", source = "order.id"),
            @Mapping(target = "orderNumber", source = "order.orderNumber"),
            @Mapping(target = "date", source = "order.date"),
            @Mapping(target = "status", source = "order.status"),
            @Mapping(target = "user", source = "order.user"),
            @Mapping(target = "orderItems", source = "order.orderItems"),
            @Mapping(target = "shippingAddress", source = "order.shippingAddress"),
            @Mapping(target = "description", source = "order.description"),
            @Mapping(target = "price", source = "order.price"),
            @Mapping(target = "updated", source = "order.updated")
    })
    SimpleOrderDTO orderToSimpleOrder(Order order);

    List<SimpleOrderDTO> orderToSimpleOrder(List<Order> orders);

    @Mappings({
            @Mapping(target = "orderNumber", source = "order.orderNumber"),
            @Mapping(target = "orderItems", source = "order.orderItems"),
            @Mapping(target = "shippingAddress", source = "order.shippingAddress"),
            @Mapping(target = "description", source = "order.description")
    })
    Order updatedOrderToOrder(UpdatedOrderDTO order);
}

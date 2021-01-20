package com.internetshop.mapper;

import com.internetshop.dto.CreationOrderDTO;
import com.internetshop.dto.SimpleOrderDTO;
import com.internetshop.dto.UpdatedOrderDTO;
import com.internetshop.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order creationOrderToOrder(CreationOrderDTO order);

    SimpleOrderDTO orderToSimpleOrder(Order order);

    List<SimpleOrderDTO> orderToSimpleOrder(List<Order> orders);

    Order updatedOrderToOrder(UpdatedOrderDTO order);
}

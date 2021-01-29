package com.internetshop.dto;

import com.internetshop.mysqlModel.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreationOrderDTO {

    private List<OrderItem> orderItems;

    @NotEmpty(message = "{error.mandatory-shipping-address}")
    private String shippingAddress;

    private String description;

    @AssertFalse(message = "{error.product-is-not-selected}")
    public boolean isOrderItemsEmpty() {
        return orderItems.isEmpty();
    }
}
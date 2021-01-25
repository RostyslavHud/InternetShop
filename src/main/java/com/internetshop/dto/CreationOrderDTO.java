package com.internetshop.dto;

import com.internetshop.model.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class CreationOrderDTO {

    private List<OrderItem> orderItems;

    @NotEmpty(message = "Shipping address is mandatory")
    private String shippingAddress;

    private String description;

    @AssertFalse(message = "You nothing buy")
    public boolean isOrderItemsEmpty() {
        return orderItems.isEmpty();
    }
}
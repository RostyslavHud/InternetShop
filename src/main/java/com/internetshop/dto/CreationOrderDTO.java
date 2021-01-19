package com.internetshop.dto;

import com.internetshop.model.OrderItem;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
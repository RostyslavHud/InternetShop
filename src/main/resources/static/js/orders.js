function update_order() {
    var selectedCheckBoxes = document.querySelectorAll('input.checkbox:checked');
    var checkedValues = Array.from(selectedCheckBoxes).map(cb => cb.value);

    let order = {
        orderItems: [],
        orderNumber: $("#order_number").val(),
        shippingAddress: $("#shipping_address").val(),
        description: $("#description").val(),
    };


    for (i = 0; i < checkedValues.length; i++) {
        let orderItem = {
            productId: checkedValues[i],
            productQty: $("#qty" + checkedValues[i]).val(),
        }
        order.orderItems[i] = orderItem;
    }

    $.ajax({
        url: '/v1/orders/' + $("#order_id").val(),
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(order),
        success: function (data) {
            if (data.messages != null) {
                if (data.messages.shippingAddress != null) {
                    $("#shipping_address_error").html(data.messages.shippingAddress);
                } else {
                    $("#shipping_address_error").html("");
                }
            } else {
                window.location.href = '/order';
            }
        }
    })
}

function delete_order(id) {
    $.ajax({
        url: '/v1/orders/' + id,
        type: 'DELETE',
        success: function () {
            show_orders();
        }
    })
}

function send_order() {
    var selectedCheckBoxes = document.querySelectorAll('input.checkbox:checked');
    var checkedValues = Array.from(selectedCheckBoxes).map(cb => cb.value);

    let order = {
        orderItems: [],
        shippingAddress: $("#shipping_address").val(),
        description: $("#description").val(),
    };

    for (i = 0; i < checkedValues.length; i++) {
        let orderItem = {
            productId: checkedValues[i],
            productQty: $("#qty" + checkedValues[i]).val(),
        }
        order.orderItems[i] = orderItem;
    }

    $.ajax({
        url: '/v1/orders/',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(order),
        success: function (data) {
            if (data.messages != null) {
                if (data.messages.shippingAddress != null) {
                    $("#shipping_address_error").html(data.messages.shippingAddress);
                } else {
                    $("#shipping_address_error").html("");
                }
                if (data.messages.orderItemsEmpty != null) {
                    $("#order_items_error").html(data.messages.orderItemsEmpty);
                } else {
                    $("#order_items_error").html("");
                }
            } else {
                window.location.href = '/order';
            }
        }
    })

}
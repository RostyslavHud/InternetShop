function show_orders() {
    $.get('/v1/orders', function (data) {
        let table = "<table border = '1'> <tr><th scope=\"col\">Order number</th><th>Order date</th>" +
            "<th>Customer name</th> <th>Shipping address</th>" +
            "<th>Description</th><th>Amount</th><th>Order status</th><th>Update BY</th><th></th><th></th></tr>";

        for (i = 0; i < data.length; i++) {
            table = table + "<tr scope=\"row\"><td>" + data[i].orderNumber + "</td><td>" + data[i].date + "</td>" +
                "<td>" + data[i].user.name + "</td><td>" + data[i].shippingAddress + "</td>\n" +
                "<td>" + data[i].description + "</td><td>" + data[i].price + "</td><td>" + data[i].status + "</td>\n" +
                "<td>" + data[i].updated + "</td>\n" +
                "<td><form action='/order/update'>\n" +
                "     <input type='hidden' name='id' value='" + data[i].id + "'/>\n" +
                "     <button type='submit' class='btn btn-secondary'>Update</button>\n" +
                "                </form>\n</td>" +
                "<td><a class='btn btn-secondary' href='/order' role='button' onclick='delete_order(" + data[i].id + ")'>Delete</a></td>" +
                "</tr>"
        }

        table = table + "</table>";

        $("#user_orders").html(table)
    })
}

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

    console.log(order);

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

$(document).ready(function () {
    show_orders();
})

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

    console.log(order);


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
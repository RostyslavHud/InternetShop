function show_orders(){
    $.get('/v1/orders', function (data){
        let table = "<table border = '1'> <tr><th scope=\"col\">Order number</th><th>Order date</th>" +
            "<th>Customer name</th> <th>Shipping address</th>" +
            "<th>Description</th><th>Order status</th><th></th><th></th></tr>";

        for (i = 0; i < data.length; i++){
            table = table + "<tr scope=\"row\"><td>" + data[i].orderNumber + "</td><td>" + data[i].date + "</td>" +
                "<td>" + data[i].user.name + "</td><td>" + data[i].shippingAddress + "</td>" +
                "<td>" + data[i].description + "</td><td>" + data[i].status + "</td>" +
                "<td><form action='/order/update'>\n" +
                "     <input type='hidden' name='id' value='"+data[i].id+"'/>\n" +
                "     <button type='submit' class='btn btn-secondary'>Update</button>\n" +
                "                </form>\n</td>" +
                "<td><a class='btn btn-secondary' href='/order' role='button' onclick='delete_order("+ data[i].id+")'>Delete</a></td>" +
                "</tr>"
        }

        table = table + "</table>";

        $("#user_orders").html(table)
    })
}

function update_order(){
    let order = {
        orderItems:[{
            product:{
                name: $("#products").val(),
            },
            productQty: $("#product_qty").val(),
        }],
        orderNumber: $("#order_number").val(),
        shippingAddress: $("#shipping_address").val(),
        description: $("#description").val(),
    };

    $.ajax({
        url: '/v1/orders',
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(order),
        success: function (){
            show_orders();
        }
    })
}

function delete_order(id){
    $.ajax({
        url: '/v1/orders/' + id,
        type: 'DELETE',
        success: function (){
            show_orders();
        }
    })
}

$(document).ready(function () {
    show_orders();
})

function send_order(){
    let order = {
        orderItems:[{
            product:{
                name: $("#products").val(),
            },
            productQty: $("#product_qty").val(),
        }],
        shippingAddress: $("#shipping_address").val(),
        description: $("#description").val(),
    };

    $.ajax({
        url: '/v1/orders',
        dataType: 'json',
        type: 'POST',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify(order),
        success: function (){
            show_orders();
        }
    })
}
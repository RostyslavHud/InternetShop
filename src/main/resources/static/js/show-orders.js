function show_orders(id) {

    $.get('/v1/orders' + '?page=' + --id + '&size=' + $("#order_count").val()
        + '&sort=' + $("#order_sort").val() + ',' + $("#sort").val(), function (data) {


        let table;

        for (i = 0; i < data.content.length; i++) {
            table += "<tr scope=\"row\"><td>" + data.content[i].orderNumber + "</td><td>" + data.content[i].date + "</td>" +
                "<td>" + data.content[i].user.name + "</td><td>" + data.content[i].shippingAddress + "</td>\n" +
                "<td>" + data.content[i].description + "</td><td>" + data.content[i].price + "</td><td>" + data.content[i].status + "</td>\n" +
                "<td>" + data.content[i].updated + "</td>\n" +
                "<td><form action='/order/update'>\n" +
                "     <input type='hidden' name='id' value='" + data.content[i].id + "'/>\n" +
                "     <button type='submit' class='btn btn-secondary'>" + buttonUpdate + "</button>\n" +
                "                </form>\n</td>" +
                "<td><a class='btn btn-secondary' href='/order' role='button' onclick='delete_order(" + data.content[i].id + ")'>" + buttonDelete + "</a></td>" +
                "</tr>"
        }


        $("#user_orders tr:not(:first)").remove();
        $("#user_orders").append(table);
        show_order_pagination(data);
    })
}

$(function () {
    $("#order_count").on("change", function () {
        show_orders();
    })
});

$(function () {
    $("#order_sort").on("change", function () {
        show_orders();
    })
});

$(function () {
    $("#sort").on("change", function () {
        show_orders();
    })
});

$(document).ready(function () {
    show_orders();
})
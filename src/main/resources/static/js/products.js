$(function () {
    $("#category_list").on("change", function () {
        get_products()
    })
});

$(function () {
    $("#product_count").on("change", function () {
        get_products()
    })
});

function get_products(id) {
    $.get('/v1/products/' + $("#category_list").val() + '?page=' + --id + '&size=' + $("#product_count").val(), function (data) {

        let table;
        for (i = 0; i < data.content.length; i++) {
            table += "<tr scope=\"row\">" +
                "<td>" + data.content[i].name + "</td>" +
                "<td>" + data.content[i].price + "</td>" +
                "<td><input id = 'qty" + data.content[i].id + "' value='1'/></td>" +
                "<td><input class='checkbox' value='" + data.content[i].id + "' type='checkbox'></td></tr>";
        }
        $("#product_list tr:not(:first)").remove();
        $("#product_list").append(table);
        show_product_pagination(data);
    })
}

function show_categories() {
    $.get('/v1/categories', function (data) {

        let select;
        for (i = 0; i < data.length; i++) {
            select += "<option value='" + data[i].id + "'> " + data[i].name + " </option>"
        }

        $("#category_list").append(select)
    })
}

$(document).ready(function () {
    show_categories();
})
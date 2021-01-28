$(function () {
    $("#category_list").on("change", function () {
        $.get('/v1/products/' + this.value, function (data) {

            let table = "<table id='product_list'> " +
                "<tr><th scope=\"col\">Product name</th><th>Product price</th> <th>QTY</th> <th></th>" +
                "<th scope=\"col\">Product name</th><th>Product price</th> <th>QTY</th> <th></th>" +
                "<th scope=\"col\">Product name</th><th>Product price</th> <th>QTY</th> <th></th></tr>";
            let count = 0;
            table = table + "</tr><tr scope=\"row\">";
            for (i = 0; i < data.length; i++) {
                if (count === 3) {
                    table = table + "</tr><tr scope=\"row\">";
                    count = 0;
                }
                table = table + "<td>" + data[i].name + "</td>" +
                    "<td>" + data[i].price + "</td>" +
                    "<td><input id = 'qty" + data[i].id + "' value='1'/></td>" +
                    "<td><input class='checkbox' value='" + data[i].id + "' type='checkbox'></td>";
                count++;
            }

            table = table + "</tr></table>"

            $("#product_list").html(table)
        })
    })
});

function show_categories() {
    $.get('/v1/categories', function (data) {

        let select = "<select id='category_list'> <option>Please select category</option>";
        for (i = 0; i < data.length; i++) {
            select = select + "<option value='" + data[i].id + "'> " + data[i].name + " </option>"
        }

        select = select + "</select>"

        $("#category_list").html(select)
    })
}

function send_product() {
    var selectedCheckBoxes = document.querySelectorAll('input.checkbox:checked');
    var checkedValues = Array.from(selectedCheckBoxes).map(cb => cb.value);

    let product = {
        name: $("#product_name").val(),
        price: $("#price").val(),
        categories: []
    };

    for (i = 0; i < checkedValues.length; i++) {
        let category = {
            id: checkedValues[i]
        }
        product.categories[i] = category;
    }

    $.ajax({
        url: '/v1/products/',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(product),
        success: function (data) {
            if (data.messages != null) {
                if (data.messages.name != null) {
                    $("#product_name_error").html(data.messages.name);
                } else {
                    $("#shipping_address_error").html("");
                }
            } else {
                window.location.href = '/order';
            }
        }
    })
}

$(document).ready(function () {
    show_categories();
})
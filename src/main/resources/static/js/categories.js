function show_categories() {
    $.get('/v1/categories', function (data) {

        let table;
        for (i = 0; i < data.length; i++) {
            table += "<tr scope=\"row\">" +
                "<td>" + data[i].name + "</td>" +
                "<td><input class='checkbox' value='" + data[i].id + "' type='checkbox'></td>" +
                "</tr>"
        }

        $("#category_list").append(table)
    })
}


$(document).ready(function () {
    show_categories();
})

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
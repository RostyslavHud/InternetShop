function show_products(){
    $.get('/v1/products', function (data){

        let table = "<table id='product_list' border = '1'> <tr><th scope=\"col\">Product name</th><th>Product price</th>" +
        "<th>QTY</th> <th></th>";
        for (i = 0; i < data.length; i++){
            table = table + "<tr scope=\"row\">" +
                "<td>" + data[i].name + "</td>" +
                "<td>" + data[i].price + "</td>" +
                "<td><input id = 'qty" + data[i].id + "' value='1'/></td>" +
                "<td><input class='checkbox' value='" + data[i].id + "' type='checkbox'></td>" +
                "</tr>"
        }

        table = table + "</table>"

        $("#products").html(table)
    })
}

$(document).ready(function (){
    show_products()
})
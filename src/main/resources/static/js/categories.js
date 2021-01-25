function show_categories() {
    $.get('/v1/categories', function (data) {

        let table = "<table id='category_list' border = '1'> <tr><th scope=\"col\">Category name</th> <th></th>";
        for (i = 0; i < data.length; i++) {
            table = table + "<tr scope=\"row\">" +
                "<td>" + data[i].name + "</td>" +
                "<td><input class='checkbox' value='" + data[i].id + "' type='checkbox'></td>" +
                "</tr>"
        }

        table = table + "</table>"

        $("#category_list").html(table)
    })
}


$(document).ready(function () {
    show_categories();
})
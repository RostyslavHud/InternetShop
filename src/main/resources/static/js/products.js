function show_products(){
    $.get('/v1/products', function (data){
        let select = "<select><option>None</optiton>";

        for (i = 0; i < data.length; i++){
            select = select + "<option>"+ data[i].name +"</option>"
        }

        select = select + "</select>"

        $("#products").html(select)
    })
}

$(document).ready(function (){
    show_products()
})
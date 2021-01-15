function show_products(){
    $.get('/v1/products', function (data){
        let select = "<select><option value='0'>None</optiton>";

        for (i = 0; i < data.length; i++){
            select = select + "<option value='"+ data[i].id +"'>"+ data[i].name +"</option>"
        }

        select = select + "</select>"

        $("#products").html(select)
    })
}

$(document).ready(function (){
    show_products()
})
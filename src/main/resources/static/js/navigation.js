function show_user(){
    $.get('/v1/user', function (data){
        console.log(data);

        let button = "<div class=\"collapse navbar-collapse\" id=\"navbarSignin\">\n" +
            "            <ul class=\"navbar-nav mr-auto\">\n" +
            "                <li class=\"nav-item active\">\n" +
            "                    <a class=\"nav-link\" href=\"/\">Home Page<span class=\"sr-only\"></span></a>\n" +
            "                </li>\n" +
            "            </ul>\n" +
            "        </div>";

        if (data == null){
            button = button + "<div class=\"collapse navbar-collapse\" id=\"navbarSignin\">\n" +
                "            <ul class=\"navbar-nav mr-auto\">\n" +
                "                <li class=\"nav-item active\">\n" +
                "                    <a class=\"nav-link\" href=\"/login\">Login<span class=\"sr-only\"></span></a>\n" +
                "                </li>\n" +
                "            </ul>\n" +
                "        </div>";
        }else {
            button = button + "<div class=\"collapse navbar-collapse\" id=\"navbarStore\">\n" +
                "            <ul class=\"navbar-nav mr-auto\">\n" +
                "                <li class=\"nav-item active\">\n" +
                "                    <a class=\"nav-link\" href=\"/order\">Order<span class=\"sr-only\"></span></a>\n" +
                "                </li>\n" +
                "            </ul>\n" +
                "        </div>" +
                "<div class=\"collapse navbar-collapse\" id=\"navbarLogout\" hidden>\n" +
                "            <ul class=\"navbar-nav mr-auto\">\n" +
                "                <li class=\"nav-item active\">\n" +
                "                    <a class=\"nav-link\" href=\"/logout\">("+ data.name +") Logout<span class=\"sr-only\"></span></a>\n" +
                "                </li>\n" +
                "            </ul>\n" +
                "        </div>";
        }
        $("#logB").html(button);

    })
}

$(document).ready(function () {
    show_user();
})
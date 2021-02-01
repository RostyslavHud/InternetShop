function show_user() {
    $.get('/v1-public/user', function (data) {

        if (data.name == null) {
            $("#navbarProduct").hide();
            $("#navbarLogout").hide();
            $("#navbarOrder").hide();
        } else {
            $("#navbarLogin").hide();
            $("#navbarRegistration").hide();
            $("#navbarLogout").show();
            $("#navbarOrder").show();
            if (data.role === "ADMIN") {
                $("#navbarProduct").show();
            }
        }
    })

}

$(document).ready(function () {
    show_user();
    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption !== '') {
            window.location.replace('?lang=' + selectedOption);
        }
    });
})
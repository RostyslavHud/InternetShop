function send_user() {
    let user = {
        name: $("#username").val(),
        password: $("#password").val(),
        confirmPassword: $("#confirm_password").val(),
        firstName: $("#first_name").val(),
        lastName: $("#last_name").val(),
        email: $("#email").val(),
        age: $("#age").val()
    };

    $.ajax({
        url: '/v1-public/add',
        dataType: 'json',
        type: 'POST',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function (data) {
            if (data.messages != null) {
                if (data.messages.name != null) {
                    $("#username_error").html(data.messages.name);
                } else {
                    $("#username_error").html("");
                }
                if (data.messages.password != null) {
                    $("#password_error").html(data.messages.password);
                } else {
                    $("#password_error").html("");
                }
                if (data.messages.confirmPassword != null) {
                    $("#password_confirm_error").html(data.messages.confirmPassword);
                } else if (data.messages.confirmedPassword != null) {
                    $("#password_confirm_error").html(data.messages.confirmedPassword);
                } else {
                    $("#password_confirm_error").html("");
                }
                if (data.messages.email != null) {
                    $("#email_error").html(data.messages.email);
                } else {
                    $("#email_error").html("");
                }
                if (data.messages.firstName != null) {
                    $("#first_name_error").html(data.messages.firstName);
                } else {
                    $("#first_name_error").html("");
                }
                if (data.messages.lastName != null) {
                    $("#last_name_error").html(data.messages.lastName);
                } else {
                    $("#last_name_error").html("");
                }
                if (data.messages.age != null) {
                    $("#age_error").html(data.messages.age);
                } else {
                    $("#age_error").html("");
                }

            } else {
                window.location.href = '/success-registration'
            }
        }
    })
}

function reset_user() {
    let user = {
        email: $("#email").val(),
    };

    $.ajax({
        url: '/v1-public/reset',
        dataType: 'json',
        type: 'PUT',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function (data) {
            if (data.messages != null) {
                if (data.messages.email != null) {
                    $("#email_error").html(data.messages.email);
                } else {
                    $("#email_error").html("");
                }

            } else {
                window.location.href = '/success-reset-user'
            }
        }
    })
}

function reset_password() {
    let user = {
        password: $("#password").val(),
        confirmPassword: $("#confirm_password").val(),
    };

    $.ajax({
        url: '/v1-public/reset-password/' + $("#token").val(),
        dataType: 'json',
        type: 'PUT',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function (data) {
            if (data.messages != null) {
                if (data.messages.password != null) {
                    $("#password_error").html(data.messages.password);
                } else {
                    $("#password_error").html("");
                }
                if (data.messages.confirmPassword != null) {
                    $("#password_confirm_error").html(data.messages.confirmPassword);
                } else if (data.messages.confirmedPassword != null) {
                    $("#password_confirm_error").html(data.messages.confirmedPassword);
                } else {
                    $("#password_confirm_error").html("");
                }

            } else {
                window.location.href = '/success-reset-password'
            }
        }
    })
}
function show_order_pagination(data) {
    let first = 1;
    let next = data.number + 2;
    let next2 = data.number + 3
    let last = data.totalPages;
    let previous = data.number;
    let previous2 = data.number - 1;
    let current = data.number + 1;

    let pag;

    if (data.totalPages === 1) {
        pag = "<li class='page-item disabled'><a class='page-link' id = '" + first + "' href='#' onclick='show_orders(id)'>" + buttonFirst + "</a></li>\n" +
            "<li class='page-item disabled'><a class='page-link' id = '" + previous + "' href='#' onclick='show_orders(id)'>" + buttonPrevious + "</a></li>\n" +
            "<li class='page-item disabled'><a class='page-link' href='#'>Page:" + current + " of " + last + "</a></li>\n" +
            "<li class='page-item disabled'><a class='page-link' id = '" + next + "' href='#' onclick='show_orders(id)'>" + buttonNext + "</a></li>\n" +
            "<li class='page-item disabled'><a class='page-link' id = '" + last + "' href='#' onclick='show_orders(id)'>" + buttonLast + "</a></li>";
    } else if (data.totalPages === 2) {
        if (data.number === 0) {
            pag = "<li class='page-item disabled'><a class='page-link' id = '" + first + "' href='#' onclick='show_orders(id)'>" + buttonFirst + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' id = '" + previous + "' href='#' onclick='show_orders(id)'>" + buttonPrevious + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' href='#'>Page:" + current + " of " + last + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' href='#' onclick='show_orders(text)'>" + next + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' id = '" + next + "' href='#' onclick='show_orders(id)'>" + buttonNext + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' id = '" + last + "' href='#' onclick='show_orders(id)'>" + buttonLast + "</a></li>";
        } else {
            pag = "<li class='page-item'><a class='page-link' id = '" + first + "' href='#' onclick='show_orders(id)'>" + buttonFirst + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' id = '" + previous + "' href='#' onclick='show_orders(id)'>" + buttonPrevious + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' href='#' onclick='show_orders(text)'>" + previous + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' href='#'>Page:" + current + " of " + last + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' id = '" + next + "' href='#' onclick='show_orders(id)'>" + buttonNext + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' id = '" + last + "' href='#' onclick='show_orders(id)'>" + buttonLast + "</a></li>";
        }

    } else {
        if (current === first) {
            pag = "<li class='page-item disabled'><a class='page-link' id = '" + first + "' href='#' onclick='show_orders(id)'>" + buttonFirst + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' id = '" + previous + "' href='#' onclick='show_orders(id)'>" + buttonPrevious + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' href='#'>Page:" + current + " of " + last + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' href='#' onclick='show_orders(text)'>" + next + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' href='#' onclick='show_orders(text)'>" + next2 + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' id = '" + next + "' href='#' onclick='show_orders(id)'>" + buttonNext + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' id = '" + last + "' href='#' onclick='show_orders(id)'>" + buttonLast + "</a></li>";
        }
        if (current === last) {
            pag = "<li class='page-item'><a class='page-link' id = '" + first + "' href='#' onclick='show_orders(id)'>" + buttonFirst + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' id = '" + previous + "' href='#' onclick='show_orders(id)'>" + buttonPrevious + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' href='#' onclick='show_orders(text)'>" + previous2 + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' href='#' onclick='show_orders(text)'>" + previous + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' href='#'>Page:" + current + " of " + last + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' id = '" + next + "' href='#' onclick='show_orders(id)'>" + buttonNext + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' id = '" + last + "' href='#' onclick='show_orders(id)'>" + buttonLast + "</a></li>";
        }
        if (current !== first && current !== last) {
            pag = "<li class='page-item'><a class='page-link' id = '" + first + "' href='#' onclick='show_orders(id)'>" + buttonFirst + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' id = '" + previous + "' href='#' onclick='show_orders(id)'>" + buttonPrevious + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' href='#' onclick='show_orders(text)'>" + previous + "</a></li>\n" +
                "<li class='page-item disabled'><a class='page-link' href='#'>Page:" + current + " of " + last + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' href='#' onclick='show_orders(text)'>" + next + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' id = '" + next + "' href='#' onclick='show_orders(id)'>" + buttonNext + "</a></li>\n" +
                "<li class='page-item'><a class='page-link' id = '" + last + "' href='#' onclick='show_orders(id)'>" + buttonLast + "</a></li>";
        }
    }

    $("#pagination").html(pag);
}

package internetshop.controller;

import internetshop.exception.RepositoryException;
import internetshop.service.OrderService;
import internetshop.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class PageController {

    @Autowired
    OrderService orderService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/order")
    public String getOrdersPage() {
        return "account/order/orders";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @GetMapping("/new_order")
    public String getNewOrderPage() {
        return "account/order/create_order";
    }

    @GetMapping("/order/update")
    public String getUpdateForm(@RequestParam("id") long id, Model model) throws ServiceException, RepositoryException {

        model.addAttribute(orderService.getById(id));
        return "account/order/update_order";
    }

}

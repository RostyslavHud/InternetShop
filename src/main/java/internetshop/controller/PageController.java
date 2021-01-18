package internetshop.controller;

import internetshop.service.OrderService;
import internetshop.exception.ServiceException;
import internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class PageController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

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

    @GetMapping("/new-order")
    public String getNewOrderPage() {
        return "account/order/create-order";
    }

    @GetMapping("/order/update")
    public String getUpdateForm(@RequestParam("id") long id, Model model) throws ServiceException {

        model.addAttribute(orderService.getById(id));
        return "account/order/update-order";
    }

    @GetMapping("/confirm/{token}")
    public String confirmRegistration(@PathVariable String token) throws ServiceException {
        userService.confirmRegistration(token);
        return "account/success/confirm-registration";
    }

    @GetMapping("/success-registration")
    public String getSuccessRegistrationPage() {
        return "account/success/registration";
    }
}

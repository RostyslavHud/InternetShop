package com.internetshop.controller;

import com.internetshop.service.OrderService;
import com.internetshop.exception.ServiceException;
import com.internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String getConfirmRegistration(@PathVariable String token) throws ServiceException {
        userService.confirmRegistration(token);
        return "account/success/confirm-registration";
    }

    @GetMapping("/reset/{token}")
    public String getResetPasswordPage(@PathVariable String token, Model model) {
        model.addAttribute(token);
        return "account/user/reset-password";
    }

    @GetMapping("/success-registration")
    public String getSuccessRegistrationPage() {
        return "account/success/registration";
    }

    @GetMapping("/success-reset-user")
    public String getSuccessResetUser() {
        return "account/success/reset-user";
    }

    @GetMapping("/success-reset-password")
    public String getSuccessResetPassword() {
        return "account/success/reset-password";
    }

    @GetMapping("/new-product")
    public String getNewProductPage() {
        return "product/create-product";
    }

    @GetMapping("/reset-user")
    public String getResetUserPage() {
        return "account/user/reset-user";
    }
}

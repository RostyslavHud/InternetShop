package internetshop.rest;

import internetshop.model.User;
import internetshop.exception.ServiceException;
import internetshop.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class UserRestController {

    private final UserService userService;
    public UserRestController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/user")
    public Optional<User> getSessionUser() throws ServiceException {
        Optional<User> user;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        user = userService.findByName(name);

        return user;
    }

}

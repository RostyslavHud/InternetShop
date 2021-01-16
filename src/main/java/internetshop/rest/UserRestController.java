package internetshop.rest;

import internetshop.model.User;
import internetshop.exception.ServiceException;
import internetshop.model.VerificationToken;
import internetshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;


@RestController
@RequestMapping("/v1-public/")
@Slf4j
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public User getSessionUser(@AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        User user = new User();
        if (userDetails != null) {
            user = userService.findByName(userDetails.getUsername());
        }
        return user;
    }

    @PostMapping("/add")
    public User addUser(@Valid @RequestBody User user) throws ServiceException, MessagingException {
        userService.addNewUser(user);
        return user;
    }

    @PostMapping("/confirm")
    public VerificationToken confirmRegistration(@Valid @RequestBody VerificationToken verificationToken) throws ServiceException {
        userService.confirmRegistration(verificationToken);
        return verificationToken;
    }
}

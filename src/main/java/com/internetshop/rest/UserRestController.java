package com.internetshop.rest;

import com.internetshop.dto.RegistrationUserDTO;
import com.internetshop.dto.SimpleUserDTO;
import com.internetshop.mapper.UserMapper;
import com.internetshop.model.User;
import com.internetshop.exception.ServiceException;
import com.internetshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserMapper userMapper;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public SimpleUserDTO getSessionUser(@AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        User user = new User();
        if (userDetails != null) {
            user = userService.findByName(userDetails.getUsername());
        }

        return userMapper.userToSimpleUser(user);
    }

    @PostMapping("/add")
    public RegistrationUserDTO addUser(@Valid @RequestBody RegistrationUserDTO registrationUserDTO) throws ServiceException, MessagingException {
        User user = userMapper.registrationUserToUser(registrationUserDTO);
        userService.addNewUser(user);
        return registrationUserDTO;
    }
}

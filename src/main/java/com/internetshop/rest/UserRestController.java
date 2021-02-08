package com.internetshop.rest;

import com.internetshop.dto.EmailUserDTO;
import com.internetshop.dto.PasswordUserDTO;
import com.internetshop.dto.RegistrationUserDTO;
import com.internetshop.dto.SimpleUserDTO;
import com.internetshop.mapper.UserMapper;
import com.internetshop.mysqlModel.User;
import com.internetshop.exception.ServiceException;
import com.internetshop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1-public/")
public class UserRestController {

    private final UserService userService;

    @Autowired
    UserMapper userMapper;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    @Operation(summary = "Get session customer")
    @ApiResponse(responseCode = "200", description = "Get session customer",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleUserDTO.class))})
    public SimpleUserDTO getSessionUser(@Parameter(description = "Authorized customer")
                                        @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        User user = new User();
        if (userDetails != null) {
            user = userService.findByName(userDetails.getUsername());
        }
        return userMapper.userToSimpleUser(user);
    }

    @PostMapping("/add")
    @Operation(summary = "Add new customer")
    @ApiResponse(responseCode = "200", description = "Add new customer",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationUserDTO.class))})
    public RegistrationUserDTO addUser(@Parameter(description = "New customer")
                                       @Valid @RequestBody RegistrationUserDTO registrationUserDTO) throws ServiceException, MessagingException {
        User user = userMapper.registrationUserToUser(registrationUserDTO);
        userService.addNewUser(user);
        return registrationUserDTO;
    }

    @PutMapping("/reset")
    @Operation(summary = "Reset user")
    @ApiResponse(responseCode = "200", description = "Reset user when it locked",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EmailUserDTO.class))})
    public EmailUserDTO resetUser(@Parameter(description = "User's email which should be reset")
                                  @Valid @RequestBody EmailUserDTO emailUserDTO) throws ServiceException, MessagingException {
        userService.resetUser(userMapper.emailUserToUser(emailUserDTO));
        return emailUserDTO;
    }

    @PutMapping("/reset-password/{token}")
    @Operation(summary = "Reset user's password")
    @ApiResponse(responseCode = "200", description = "Reset user's password",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PasswordUserDTO.class))})
    public PasswordUserDTO resetUserPassword(@Parameter(description = "Token for user which reset password")
                                             @PathVariable String token,
                                             @Parameter(description = "User's new password")
                                             @Valid @RequestBody PasswordUserDTO passwordUserDTO) throws ServiceException {

        userService.resetPassword(token, userMapper.passwordUserToUser(passwordUserDTO));
        return passwordUserDTO;
    }
}

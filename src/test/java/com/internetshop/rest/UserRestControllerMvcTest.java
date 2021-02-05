package com.internetshop.rest;

import com.internetshop.dto.EmailUserDTO;
import com.internetshop.dto.PasswordUserDTO;
import com.internetshop.dto.RegistrationUserDTO;
import com.internetshop.dto.SimpleUserDTO;
import com.internetshop.mapper.UserMapper;
import com.internetshop.mysqlModel.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(UserRestController.class)
class UserRestControllerMvcTest extends AbstractRestControllerMvcTest {

    @MockBean
    UserMapper userMapper;

    @Test
    void getSessionUser() throws Exception {
        User user = new User();
        when(userService.findByName("admin")).thenReturn(user);
        when(userMapper.userToSimpleUser(user)).thenReturn(new SimpleUserDTO());

        mockMvc.perform(get("/v1-public/user")).andExpect(status().isOk());
    }

    @Test
    void newUser() throws Exception {
        String expectedResult = "{\"name\":\"ted\",\"password\":\"admiN123\",\"confirmPassword\":\"admiN123\"," +
                "\"firstName\":\"Admin\",\"lastName\":\"Admin\",\"email\":\"admin@gmail.com\",\"age\":22}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(post("/v1-public/add")
                .content(asJsonString(new RegistrationUserDTO("ted", "admiN123",
                        "admiN123", "Admin",
                        "Admin", "admin@gmail.com", 22)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void incorrectNameError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"name\":\"Name shouldn't have special symbols " +
                "and it's name should be from 3 to 16 symbols.\"}}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(post("/v1-public/add")
                .content(asJsonString(new RegistrationUserDTO("te", "admiN123",
                        "admiN123", "Admin",
                        "Admin", "admin@gmail.com", 22)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void incorrectPasswordError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"password\":\"Password should be strong and it should " +
                "have 1 big letter, 1 small letter and 1 numeral and it's length should be min 8 symbols\"}}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(post("/v1-public/add")
                .content(asJsonString(new RegistrationUserDTO("ted", "admi",
                        "admi", "Admin",
                        "Admin", "admin@gmail.com", 22)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void passwordsIsNotSameError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"confirmedPassword\":\"Password and " +
                "Confirm password can be same\"}}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(post("/v1-public/add")
                .content(asJsonString(new RegistrationUserDTO("ted", "adMin123",
                        "admin123", "Admin",
                        "Admin", "admin@gmail.com", 22)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void emptyConfirmPasswordError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"confirmedPassword\":\"Password and Confirm password " +
                "can be same\",\"confirmPassword\":\"Confirm Password is mandatory\"}}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(post("/v1-public/add")
                .content(asJsonString(new RegistrationUserDTO("ted", "adMin123",
                        "", "Admin",
                        "Admin", "admin@gmail.com", 22)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void incorrectFirstNameError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"firstName\":\"First name name " +
                "should have only symbols and it's length should be min 2 symbols\"}}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(post("/v1-public/add")
                .content(asJsonString(new RegistrationUserDTO("ted", "adMin123",
                        "adMin123", "A",
                        "Admin", "admin@gmail.com", 22)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void incorrectLastNameError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"lastName\":\"Last name name should have only " +
                "symbols and it's length should be min 2 symbols\"}}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(post("/v1-public/add")
                .content(asJsonString(new RegistrationUserDTO("ted", "adMin123",
                        "adMin123", "Admin",
                        "A", "admin@gmail.com", 22)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void emailWithoutDogError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"email\":\"Your email isn't correct\"}}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(post("/v1-public/add")
                .content(asJsonString(new RegistrationUserDTO("ted", "adMin123",
                        "adMin123", "Admin",
                        "Admin", "admingmail.com", 22)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void emailWithIncorrectAddressError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"email\":\"Your email isn't correct\"}}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(post("/v1-public/add")
                .content(asJsonString(new RegistrationUserDTO("ted", "adMin123",
                        "adMin123", "Admin",
                        "Admin", "admin@gmailcom", 22)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void incorrectAgeError() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"age\":\"must be greater than or equal to 14\"}}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(post("/v1-public/add")
                .content(asJsonString(new RegistrationUserDTO("ted", "adMin123",
                        "adMin123", "Admin",
                        "Admin", "admin@gmail.com", 0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void incorrectEmailErrorInResetPassword() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"email\":\"Your email isn't correct\"}}";

        User user = new User();
        when(userMapper.emailUserToUser(new EmailUserDTO())).thenReturn(user);
        doNothing().when(userService).resetUser(user);

        mockMvc.perform(put("/v1-public/reset")
                .content(asJsonString(new EmailUserDTO("sdfsdf")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void emailWithoutDogErrorInResetPassword() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"email\":\"Your email isn't correct\"}}";

        User user = new User();
        when(userMapper.emailUserToUser(new EmailUserDTO())).thenReturn(user);
        doNothing().when(userService).resetUser(user);

        mockMvc.perform(put("/v1-public/reset")
                .content(asJsonString(new EmailUserDTO("sdfsdf.sdfgsdf")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void incorrectPasswordErrorInResetPassword() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"password\":\"Password should be strong and it should " +
                "have 1 big letter, 1 small letter and 1 numeral and it's length should be min 8 symbols\"}}";

        User user = new User();
        when(userMapper.passwordUserToUser(new PasswordUserDTO())).thenReturn(user);
        doNothing().when(userService).resetPassword("", user);

        mockMvc.perform(put("/v1-public/reset-password/{}", "")
                .content(asJsonString(new PasswordUserDTO("sdfa", "sdfa")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void passwordsIsNotSameErrorInResetPassword() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"confirmedPassword\":\"Password and " +
                "Confirm password can be same\"}}";

        User user = new User();
        when(userMapper.passwordUserToUser(new PasswordUserDTO())).thenReturn(user);
        doNothing().when(userService).resetPassword("", user);

        mockMvc.perform(put("/v1-public/reset-password/{}", "")
                .content(asJsonString(new PasswordUserDTO("adMin123", "sdfa")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    @Test
    void emptyConfirmPasswordErrorInResetPassword() throws Exception {
        String expectedResult = "{\"code\":1102,\"messages\":{\"confirmedPassword\":\"Password and Confirm password " +
                "can be same\",\"confirmPassword\":\"Confirm Password is mandatory\"}}";

        User user = new User();
        when(userMapper.registrationUserToUser(new RegistrationUserDTO())).thenReturn(user);
        doNothing().when(userService).addNewUser(user);

        mockMvc.perform(put("/v1-public/reset-password/{}", "")
                .content(asJsonString(new PasswordUserDTO("adMin123", "")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }



}

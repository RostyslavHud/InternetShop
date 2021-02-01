package com.internetshop.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internetshop.config.SecurityConfigForTest;
import com.internetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Import(SecurityConfigForTest.class)
@WithMockUser(username = "admin", password = "1", authorities = "ADMIN")
public class AbstractRestControllerMvcTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

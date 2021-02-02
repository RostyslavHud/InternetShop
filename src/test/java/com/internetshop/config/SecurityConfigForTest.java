package com.internetshop.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration()
public class SecurityConfigForTest {

    @Primary
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        User basicUser = new User("admin", new BCryptPasswordEncoder(12).encode("2"),
                Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
        return new InMemoryUserDetailsManager(basicUser);
    }

}

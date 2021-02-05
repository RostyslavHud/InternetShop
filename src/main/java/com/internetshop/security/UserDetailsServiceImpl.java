package com.internetshop.security;

import com.internetshop.mysqlModel.User;
import com.internetshop.mysqlRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;

        User user = userRepository.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user found with username: " + name);
        }
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(),
                user.isActive(),
                accountNonExpired,
                credentialsNonExpired,
                user.isAccountNonLocked(),
                roles);
    }
}

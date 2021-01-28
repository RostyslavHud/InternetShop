package com.internetshop.dto;

import com.internetshop.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserDTO {

    private Long id;
    private String name;
    private Role role;
}
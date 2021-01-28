package com.internetshop.mapper;

import com.internetshop.dto.RegistrationUserDTO;
import com.internetshop.dto.SimpleUserDTO;
import com.internetshop.mysqlModel.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    SimpleUserDTO userToSimpleUser(User user);

    User registrationUserToUser(RegistrationUserDTO user);
}

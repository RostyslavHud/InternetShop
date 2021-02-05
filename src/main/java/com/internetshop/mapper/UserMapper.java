package com.internetshop.mapper;

import com.internetshop.dto.EmailUserDTO;
import com.internetshop.dto.PasswordUserDTO;
import com.internetshop.dto.RegistrationUserDTO;
import com.internetshop.dto.SimpleUserDTO;
import com.internetshop.mysqlModel.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    SimpleUserDTO userToSimpleUser(User user);

    User registrationUserToUser(RegistrationUserDTO user);

    User emailUserToUser(EmailUserDTO user);

    User passwordUserToUser(PasswordUserDTO user);
}

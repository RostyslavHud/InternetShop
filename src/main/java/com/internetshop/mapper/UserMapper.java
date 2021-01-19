package com.internetshop.mapper;

import com.internetshop.dto.RegistrationUserDTO;
import com.internetshop.dto.SimpleUserDTO;
import com.internetshop.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", source = "user.id"),
            @Mapping(target = "name", source = "user.name")
    })
    SimpleUserDTO userToSimpleUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.name"),
            @Mapping(target = "password", source = "user.password"),
            @Mapping(target = "firstName", source = "user.firstName"),
            @Mapping(target = "lastName", source = "user.lastName"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "age", source = "user.age")
    })
    User registrationUserToUser(RegistrationUserDTO user);
}

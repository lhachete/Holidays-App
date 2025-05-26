package com.rob.driving.mappers;

import com.rob.domain.models.User;
import com.rob.driving.dtos.LoginRequest;
import com.rob.driving.dtos.LoginResponse;
import com.rob.driving.dtos.RegisterRequest;
import com.rob.driving.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = {RoleDTOMapper.class})
public interface UserDTOMapper {

    @Mapping(source = "role" , target = "role")
    @Mapping(source = "hashedPassword" , target = "hashedPassword")
    User toUser(UserDTO userDTO);

    @Mapping(source = "role" , target = "role")
    @Mapping(source = "hashedPassword" , target = "hashedPassword")
    UserDTO toUserDTO(User user);

    @Mapping(source = "role" , target = "role")
    @Mapping(source = "password" , target = "hashedPassword")
    User toUser(RegisterRequest registerRequest);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "hashedPassword", target = "hashedPassword")
    @Mapping(source = "role" , target = "rol")
    LoginResponse toLoginResponse(UserDTO userDTO);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "hashedPassword", target = "hashedPassword")
    @Mapping(source = "role" , target = "rol")
    LoginResponse toLoginResponse(User user);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "hashedPassword", target = "hashedPassword")
    @Mapping(source = "rol" , target = "role")
    User toUser(LoginResponse loginResponse);

    @Mapping(source = "usernameOrEmail", target = "username")
    @Mapping(source = "usernameOrEmail", target = "email")
    @Mapping(source = "password", target = "hashedPassword")
    User toUser(LoginRequest loginRequest);
}

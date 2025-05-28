package com.rob.driving.mappers;

import com.rob.domain.models.User;
import com.rob.driving.dtos.LoginRequest;
import com.rob.driving.dtos.LoginResponse;
import com.rob.driving.dtos.RegisterRequest;
import com.rob.driving.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = {RoleDTOMapper.class, EmployeeDTOMapper.class})
public interface UserDTOMapper {

    @Mapping(source = "employee.name" , target = "employee.firstName")
    @Mapping(source = "employee.lastName" , target = "employee.lastName")
    @Mapping(source = "employee.personId" , target = "employee.id")
    @Mapping(source = "role" , target = "role")
    @Mapping(source = "id" , target = "id")
    @Mapping(source = "codeColor" , target = "codeColor")
    @Mapping(source = "email" , target = "email")
    @Mapping(source = "enabled" , target = "enabled")
    User toUser(UserDTO userDTO);

    @Mapping(source = "employee.firstName" , target = "employee.name")
    @Mapping(source = "employee.lastName" , target = "employee.lastName")
    @Mapping(source = "employee.id" , target = "employee.personId")
    @Mapping(source = "role" , target = "role")
    @Mapping(source = "id" , target = "id")
    @Mapping(source = "codeColor" , target = "codeColor")
    @Mapping(source = "email" , target = "email")
    @Mapping(source = "enabled" , target = "enabled")
    UserDTO toUserDTO(User user);

    @Mapping(source = "role" , target = "role")
    @Mapping(source = "password" , target = "hashedPassword")
    User toUser(RegisterRequest registerRequest);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role" , target = "rol")
    @Mapping(source = "employee" , target = "employee")
    LoginResponse toLoginResponse(UserDTO userDTO);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role" , target = "rol")
    @Mapping(source = "codeColor" , target = "codeColor")
    @Mapping(source = "employee.id" , target = "employee.personId")
    @Mapping(source = "employee.firstName" , target = "employee.name")
    @Mapping(source = "employee.lastName" , target = "employee.lastName")
    LoginResponse toLoginResponse(User user);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "rol" , target = "role")
    User toUser(LoginResponse loginResponse);

    @Mapping(source = "usernameOrEmail", target = "username")
    @Mapping(source = "usernameOrEmail", target = "email")
    @Mapping(source = "password", target = "hashedPassword")
    User toUser(LoginRequest loginRequest);
}

package com.rob.driving.mappers;

import com.rob.domain.models.User;
import com.rob.driving.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


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

    default UserCollectionResponse toUserCollectionResponse(List<User> users) {
        List<UserDTO> userDTOList = users.stream()
                .map(this::toUserDTO)
                .collect(Collectors.toList());

        return new UserCollectionResponse().data(userDTOList);
    }

    @Mapping(source = "employee.firstName" , target = "employee.name")
    @Mapping(source = "employee.lastName" , target = "employee.lastName")
    @Mapping(source = "employee.id" , target = "employee.personId")
    @Mapping(source = "role" , target = "role")
    @Mapping(source = "id" , target = "id")
    @Mapping(source = "codeColor" , target = "codeColor")
    @Mapping(source = "email" , target = "email")
    @Mapping(source = "enabled" , target = "enabled")
    UserDTO toUserDTO(User user);

    @Mapping(source = "employee.firstName" , target = "data.employee.name")
    @Mapping(source = "employee.lastName" , target = "data.employee.lastName")
    @Mapping(source = "employee.id" , target = "data.employee.personId")
    @Mapping(source = "role" , target = "data.role")
    @Mapping(source = "id" , target = "data.id")
    @Mapping(source = "codeColor" , target = "data.codeColor")
    @Mapping(source = "email" , target = "data.email")
    @Mapping(source = "enabled" , target = "data.enabled")
    UserResponse toUserResponseDTO(User user);

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

    @Mapping(source = "id", target = "data.userId")
    @Mapping(source = "username", target = "data.username")
    @Mapping(source = "email", target = "data.email")
    @Mapping(source = "role" , target = "data.rol")
    @Mapping(source = "codeColor" , target = "data.codeColor")
    @Mapping(source = "employee.id" , target = "data.employee.personId")
    @Mapping(source = "employee.firstName" , target = "data.employee.name")
    @Mapping(source = "employee.lastName" , target = "data.employee.lastName")
    LoginUserResponseDTO toLoginUserResponse(User user);
}

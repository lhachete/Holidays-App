package com.rob.driving.mappers;

import com.rob.domain.models.User;
import com.rob.driving.dtos.RegisterRequest;
import com.rob.driving.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = {RoleDTOMapper.class})
public interface UserDTOMapper {

    @Mapping(source = "role" , target = "role")
    User toUser(UserDTO userDTO);

    @Mapping(source = "role" , target = "role")
    UserDTO toUserDTO(User user);

    @Mapping(source = "role" , target = "role")
    User toUser(RegisterRequest registerRequest);
}

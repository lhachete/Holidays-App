package com.rob.main.driven.repositories.mappers;

import com.rob.domain.models.User;
import com.rob.main.driven.repositories.models.UserMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring", uses = {RoleMOMapper.class})
public interface UserMOMapper {

    @Mapping(source = "role", target = "rol")
    UserMO toUserMO(User user);

    @Mapping(source = "rol", target = "role")
    User toUser(UserMO userMO);
}

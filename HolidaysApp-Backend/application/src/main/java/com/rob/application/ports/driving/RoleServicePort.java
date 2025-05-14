package com.rob.application.ports.driving;

import com.rob.domain.models.Role;

import java.util.List;

public interface RoleServicePort {
    List<Role> getRolesByName(String name);
}

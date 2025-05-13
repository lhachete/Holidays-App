package com.rob.main.driven.repositories.adapters;

import com.rob.application.ports.driven.RoleRepositoryPort;
import com.rob.main.driven.repositories.RoleMORepositoryJpaRepository;
import com.rob.main.driven.repositories.mappers.RoleMOMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class RoleJpaRepositoryAdapter implements RoleRepositoryPort {

    private final RoleMORepositoryJpaRepository roleRepository;
    private final RoleMOMapper roleMOMapper;
}

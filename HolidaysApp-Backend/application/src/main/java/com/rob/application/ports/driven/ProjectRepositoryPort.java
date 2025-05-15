package com.rob.application.ports.driven;

import com.rob.domain.models.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProjectRepositoryPort {

    List<Project> findByNameContaining(String name);

    List<Project> findAll();
}

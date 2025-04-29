package com.rob.repositories.repository;

import com.rob.repositories.entities.Department;

public interface DepartmentRepository extends org.springframework.data.jpa.repository.JpaRepository<Department, Long> {
}
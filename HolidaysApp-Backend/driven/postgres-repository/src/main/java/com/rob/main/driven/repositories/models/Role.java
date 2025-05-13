package com.rob.main.driven.repositories.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_gen")
    @SequenceGenerator(name = "roles_id_gen", sequenceName = "roles_rol_id_seq", allocationSize = 1)
    @Column(name = "rol_id", nullable = false)
    private Integer id;

    @jakarta.validation.constraints.Size(max = 50)
    @jakarta.validation.constraints.NotNull
    @Column(name = "rol", nullable = false, length = 50)
    private String rol;

}
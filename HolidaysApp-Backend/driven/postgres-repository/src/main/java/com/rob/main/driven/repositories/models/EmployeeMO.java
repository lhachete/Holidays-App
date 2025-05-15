package com.rob.main.driven.repositories.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class EmployeeMO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dept_id", nullable = false)
    private DepartmentMO dept;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectMO project;

    @Size(max = 255)
    @NotNull
    @Column(name = "nif", nullable = false)
    private String nif;

    @NotNull
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Size(max = 255)
    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Size(max = 255)
    @NotNull
    @Column(name = "birth_city", nullable = false)
    private String birthCity;

    @Size(max = 255)
    @NotNull
    @Column(name = "personal_phone", nullable = false)
    private String personalPhone;

    @Size(max = 255)
    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(max = 255)
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Size(max = 255)
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Size(max = 255)
    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @Size(max = 255)
    @NotNull
    @Column(name = "marital_status", nullable = false)
    private String maritalStatus;

    @Size(max = 255)
    @NotNull
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Size(max = 255)
    @Column(name = "second_nationality")
    private String secondNationality;

    @Size(max = 255)
    @NotNull
    @Column(name = "primary_residence", nullable = false)
    private String primaryResidence;

    @Size(max = 255)
    @NotNull
    @Column(name = "personal_email", nullable = false)
    private String personalEmail;

    @Size(max = 255)
    @Column(name = "secondary_email")
    private String secondaryEmail;

    @Size(max = 255)
    @NotNull
    @Column(name = "work_email", nullable = false)
    private String workEmail;

}
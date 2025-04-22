package com.rob.domain.models;

import com.rob.domain.models.enums.Gender;
import com.rob.domain.models.enums.MaritalStatus;
import com.rob.domain.models.enums.Title;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Date birthday;
    private String nif;
    private String countryOfBirth;
    private String birthCity;
    private String personalPhone;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Title title;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    private String nationality;
    private String secondNationality;
    private String primaryResidence;
    private String personalEmail;
    private String secondaryEmail;
    private String workEmail;
}

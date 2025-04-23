package com.rob.domain.models.entities;

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
    @Column(name = "person_id", nullable = false)
    private Long personId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Column(name = "nif", nullable = false)
    private String nif;

    @Column(name = "country_of_birth", nullable = false)
    private String countryOfBirth;

    @Column(name = "birth_city", nullable = false)
    private String birthCity;

    @Column(name = "personal_phone", nullable = false)
    private String personalPhone;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "title", nullable = false)
    private Title title;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "second_nationality", nullable = true)
    private String secondNationality;

    @Column(name = "primary_residence", nullable = false)
    private String primaryResidence;

    @Column(name = "personal_email", nullable = false)
    private String personalEmail;

    @Column(name = "secondary_email", nullable = true)
    private String secondaryEmail;

    @Column(name = "work_email", nullable = false)
    private String workEmail;
}

package com.rob.domain.models.entities;

import com.rob.domain.models.enums.Gender;
import com.rob.domain.models.enums.MaritalStatus;
import com.rob.domain.models.enums.Title;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotNull
    @Min(value = 0)
    @Max(value = 99999)
    private Long personId;

    @NotNull
    @Min(value = 0)
    @Max(value = 99999)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @NotNull
    @Min(value = 0)
    @Max(value = 99999)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @NotNull
    @Min(value = 0)
    @Max(value = 99999)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotEmpty
    @PastOrPresent
    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @NotNull
    @Size(min = 9, max = 9)
    @Column(name = "nif", nullable = false)
    private String nif;

    @Size(min = 3, max = 100)
    @Column(name = "country_of_birth", nullable = false)
    private String countryOfBirth;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "birth_city", nullable = false)
    private String birthCity;

    @NotNull
    @Size(min = 9, max = 9)
    @Column(name = "personal_phone", nullable = false)
    private String personalPhone;

    @NotEmpty
    @Size(min = 3, max = 100)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty
    @Size(min = 3, max = 100)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "title", nullable = false)
    private Title title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Size(min = 3, max = 100)
    @Column(name = "second_nationality", nullable = true)
    private String secondNationality;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "primary_residence", nullable = false)
    private String primaryResidence;

    @NotNull
    @Size(min = 3, max = 100)
    @Email
    @Column(name = "personal_email", nullable = false)
    private String personalEmail;

    @Size(min = 3, max = 100)
    @Email
    @Column(name = "secondary_email", nullable = true)
    private String secondaryEmail;

    @NotNull
    @Size(min = 3, max = 100)
    @Email
    @Column(name = "work_email", nullable = false)
    private String workEmail;
}

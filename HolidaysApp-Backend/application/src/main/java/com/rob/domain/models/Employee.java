package com.rob.domain.models;

import com.rob.domain.models.Department;
import com.rob.domain.models.Project;
import com.rob.domain.models.User;
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
public class Employee {

    private Long personId;
    private Department department;
    private Project project;
    private User user;
    private Date birthday;
    private String nif;
    private String countryOfBirth;
    private String birthCity;
    private String personalPhone;
    private String firstName;
    private String lastName;
    private Title title;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private String nationality;
    private String secondNationality;
    private String primaryResidence;
    private String personalEmail;
    private String secondaryEmail;
    private String workEmail;
}

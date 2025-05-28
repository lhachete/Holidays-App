package com.rob.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    private Integer id;
    private Department dept;
    private Project project;
    private String nif;
    private LocalDate birthday;
    private String countryOfBirth;
    private String birthCity;
    private String personalPhone;
    private String firstName;
    private String lastName;
    private String title;
    private String gender;
    private String maritalStatus;
    private String nationality;
    private String secondNationality;
    private String primaryResidence;
    private String personalEmail;
    private String secondaryEmail;
    private String workEmail;
}

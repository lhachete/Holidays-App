package com.rob.domain.models.dtos;

import com.rob.models.Employee;
import com.rob.models.enums.Gender;
import com.rob.models.enums.MaritalStatus;
import com.rob.models.enums.Title;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private String deptName;
    private String projectName;
    private String userName;
    private String nif;
    private Date birthday;
    private String country_of_birth;
    private String birth_city;
    private String personal_phone;
    private String first_name;
    private String last_name;
    private Title title;
    private Gender gender;
    private MaritalStatus marital_status;
    private String nationality;
    private String second_nationality;
    private String primary_residence;
    private String personal_email;
    private String secondary_email;
    private String work_email;

    public EmployeeDTO(Employee employee) {
        this.deptName = employee.getDepartment().getName();
        this.projectName = employee.getProject().getProjectName();
        this.userName = employee.getUser().getUsername();
        this.nif = employee.getNif();
        this.birthday = employee.getBirthday();
        this.country_of_birth = employee.getCountryOfBirth();
        this.birth_city = employee.getBirthCity();
        this.personal_phone = employee.getPersonalPhone();
        this.first_name = employee.getFirstName();
        this.last_name = employee.getLastName();
        this.title = employee.getTitle();
        this.gender = employee.getGender();
        this.marital_status = employee.getMaritalStatus();
        this.nationality = employee.getNationality();
        this.second_nationality = employee.getSecondNationality();
        this.primary_residence = employee.getPrimaryResidence();
        this.personal_email = employee.getPersonalEmail();
        this.secondary_email = employee.getSecondaryEmail();
        this.work_email = employee.getWorkEmail();
    }

}

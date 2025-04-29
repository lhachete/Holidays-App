package com.rob.domain.models.dtos;

import com.rob.models.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    private String orgName;
    private String name;
    private String business_unit;
    private String division;
    private String cost_center;
    private String location;
    private String time_zone;

    public DepartmentDTO(Department department) {
        this.orgName = department.getOrganization().getName();
        this.name = department.getName();
        this.business_unit = department.getBusinessUnit();
        this.division = department.getDivision();
        this.cost_center = department.getCostCenter();
        this.location = department.getLocation();
        this.time_zone = department.getTimeZone();
    }

}

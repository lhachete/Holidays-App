package com.rob.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private Integer id;
    private Organization organization;
    private String name;
    private String businessUnit;
    private String division;
    private String costCenter;
    private String location;
    private String timeZone;
}

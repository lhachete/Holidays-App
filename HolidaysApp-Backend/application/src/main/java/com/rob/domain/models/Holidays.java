package com.rob.domain.models;

import com.rob.domain.models.enums.VacationState;
import com.rob.domain.models.enums.VacationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Holidays {

    private Long holidaysId;

    private User user;

    private User reviewedByAdmin;

    private LocalDate reviewDate;

    private String reviewComment;

    private LocalDate holidayStartDate;

    private LocalDate holidayEndDate;

    private VacationState vacationState;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private User createdBy;

    private User updatedBy;

    private User deletedBy;

    private Boolean isDeleted;
    private VacationType vacationType;
}

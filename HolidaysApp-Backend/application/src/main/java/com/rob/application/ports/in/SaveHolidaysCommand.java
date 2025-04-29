package com.rob.application.ports.in;

import com.rob.repositories.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.rob.models.enums.VacationState;
import com.rob.models.enums.VacationType;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveHolidaysCommand {

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

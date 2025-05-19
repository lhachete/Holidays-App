package com.rob.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Holiday {

    private Integer id;
    private User user;
    private User reviewedByAdmin;
    private LocalDate reviewDate;
    private String reviewComment;
    private LocalDate holidayStartDate;
    private LocalDate holidayEndDate;
    private String vacationType;
    private String vacationState;
    private OffsetDateTime createdAt;
    private OffsetDateTime deletedAt;
    private OffsetDateTime updatedAt;
    private User createdBy;
    private User updatedBy;
    private User deletedBy;
    private Boolean isDeleted;
}

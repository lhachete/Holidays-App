package com.rob.domain.models.dtos;

import com.rob.repositories.entities.Holidays;
import com.rob.models.enums.VacationState;
import com.rob.models.enums.VacationType;

import java.time.LocalDate;
import java.util.Date;

public class HolidayDTO {
    private LocalDate review_date;
    private String review_comment;
    private LocalDate holiday_start_date;
    private LocalDate holiday_end_date;
    private VacationType vacation_type;
    private VacationState vacation_state;
    private Date created_at;
    private Date deleted_at;
    private Date updated_at;
    private boolean is_deleted;

    public HolidayDTO(Holidays holiday) {
        this.review_date = holiday.getReviewDate();
        this.review_comment = holiday.getReviewComment();
        this.holiday_start_date = holiday.getHolidayStartDate();
        this.holiday_end_date = holiday.getHolidayEndDate();
        this.vacation_type = holiday.getVacationType();
        this.vacation_state = holiday.getVacationState();
        this.created_at = holiday.getCreatedAt();
        this.deleted_at = holiday.getDeletedAt();
        this.updated_at = holiday.getUpdatedAt();
        this.is_deleted = holiday.getIsDeleted();
    }
}

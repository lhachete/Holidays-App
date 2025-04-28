package com.rob.domain.models.dtos;

import com.rob.domain.models.entities.Holidays;
import com.rob.domain.models.enums.VacationState;
import com.rob.domain.models.enums.VacationType;

import java.time.LocalDate;
import java.util.Date;

public class HolidayDTO {
    private Date review_date;
    private String review_comment;
    private Date holiday_start_date;
    private Date holiday_end_date;
    private VacationType vacation_type;
    private VacationState vacation_state;
    private LocalDate created_at;
    private LocalDate deleted_at;
    private LocalDate updated_at;
    private boolean is_deleted;

    public HolidayDTO(Holidays holiday) {
        this.review_date = holiday.getReviewDate();
        this.review_comment = holiday.getReviewComment();
        this.holiday_start_date = holiday.getHolidayStartDate();
        this.holiday_end_date = holiday.getHolidayEndDate();
        this.vacation_type = holiday.getVacationType();
        this.vacation_state = holiday.getVacationState();
        
    }
}

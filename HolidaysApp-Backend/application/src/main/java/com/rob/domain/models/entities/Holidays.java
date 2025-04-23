package com.rob.domain.models.entities;

import com.rob.domain.models.enums.VacationState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "holidays")
public class Holidays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holidaysId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by_admin_id", nullable = true)
    private User reviewedByAdmin;

    @Column(name = "review_date")
    private Date reviewDate;

    @Column(name = "review_comment")
    private String reviewComment;

    @Column(name = "holiday_start_date", nullable = false)
    private Date holidayStartDate;

    @Column(name = "holiday_end_date", nullable = false)
    private Date holidayEndDate;

    @Column(name = "vacation_state", nullable = false)
    private VacationState vacationState;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by", nullable = true)
    private User deletedBy;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}

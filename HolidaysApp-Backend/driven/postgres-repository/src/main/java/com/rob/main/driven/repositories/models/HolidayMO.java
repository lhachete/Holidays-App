package com.rob.main.driven.repositories.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "holidays")
public class HolidayMO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserMO user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by_admin_id")
    private UserMO reviewedByAdmin;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @Size(max = 500)
    @Column(name = "review_comment", length = 500)
    private String reviewComment;

    @NotNull
    @Column(name = "holiday_start_date", nullable = false)
    private LocalDate holidayStartDate;

    @NotNull
    @Column(name = "holiday_end_date", nullable = false)
    private LocalDate holidayEndDate;

    @Size(max = 50)
    @NotNull
    @Column(name = "vacation_type", nullable = false, length = 50)
    private String vacationType;

    @Size(max = 50)
    @NotNull
    @Column(name = "vacation_state", nullable = false, length = 50)
    private String vacationState;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserMO createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private UserMO updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by")
    private UserMO deletedBy;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

}
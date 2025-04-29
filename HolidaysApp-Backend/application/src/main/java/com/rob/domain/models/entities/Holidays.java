//package com.rob.domain.models.entities;
//
//import com.rob.domain.models.enums.VacationState;
//import com.rob.domain.models.enums.VacationType;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//import java.util.Date;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "holidays")
//public class Holidays {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @NotNull
//    @Min(value = 0)
//    @Max(value = 99999)
//    private Long holidaysId;
//
//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "reviewed_by_admin_id", nullable = true)
//    private User reviewedByAdmin;
//
//    @Column(name = "review_date")
//    @PastOrPresent
//    private LocalDate reviewDate;
//
//    @Column(name = "review_comment")
//    @Size(max = 255)
//    private String reviewComment;
//
//    @FutureOrPresent
//    @Column(name = "holiday_start_date", nullable = false)
//    private LocalDate holidayStartDate;
//
//    @FutureOrPresent
//    @Column(name = "holiday_end_date", nullable = false)
//    private LocalDate holidayEndDate;
//
//    @NotNull
//    @Column(name = "vacation_state", nullable = false)
//    private VacationState vacationState;
//
//    @NotNull
//    @Column(name = "created_at", nullable = false)
//    private Date createdAt;
//
//    @FutureOrPresent
//    @Column(name = "updated_at")
//    private Date updatedAt;
//
//    @FutureOrPresent
//    @Column(name = "deleted_at")
//    private Date deletedAt;
//
//    @NotNull
//    @FutureOrPresent
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "created_by", nullable = false)
//    private User createdBy;
//
//    @FutureOrPresent
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "updated_by")
//    private User updatedBy;
//
//    @FutureOrPresent
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "deleted_by", nullable = true)
//    private User deletedBy;
//
//    @NotNull
//    @Column(name = "is_deleted", nullable = false)
//    private Boolean isDeleted;
//
//    @NotNull
//    @Column(name="vacation_type", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private VacationType vacationType;
//}

package com.rob.domain.models;

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
    @JoinColumn(name = "reviewed_by_admin_id", nullable = false)
    private User reviewedByAdmin;

    private Date reviewDate;
    private Date holidayStartDate;
    private Date holidayEndDate;

}

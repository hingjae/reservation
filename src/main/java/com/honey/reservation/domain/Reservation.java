package com.honey.reservation.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(optional = false) @JoinColumn(name = "manager_id")
    private Manager manager;


    @Column(nullable = false) private LocalDateTime reservationTime;
    @Column(length = 1000) private String memo;
    @Enumerated(EnumType.STRING) private ProcessingStatus processingStatus;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}

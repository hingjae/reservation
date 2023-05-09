package com.honey.reservation.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50) private String loginId;
    @Column(length = 50) private String password;
    @Column(length = 10) private String name;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}

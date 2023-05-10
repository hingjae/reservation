package com.honey.reservation.domain;

import com.honey.reservation.domain.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Customer extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(unique = true, length = 50) private String loginId;
    @Setter @Column(length = 50) private String password;
    @Setter @Column(length = 10) private String name;
    @Setter @Column(length = 20) private String phoneNumber;

    protected Customer() {}

    private Customer(String loginId, String password, String name, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static Customer of(String loginId, String password, String name, String phoneNumber) {
        return new Customer(loginId, password, name, phoneNumber);
    }

}
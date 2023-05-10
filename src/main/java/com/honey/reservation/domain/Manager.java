package com.honey.reservation.domain;

import com.honey.reservation.domain.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Manager extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50) private String loginId;
    @Column(length = 50) private String password;
    @Column(length = 10) private String name;

    protected Manager() {}

    private Manager(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

    public static Manager of(String loginId, String password, String name) {
        return new Manager(loginId, password, name);
    }
}

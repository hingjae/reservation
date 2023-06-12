package com.honey.reservation.domain;

import com.honey.reservation.domain.baseentity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "phoneNumber")
})
@Entity
public class Customer extends BaseTimeEntity {

    @Id
    @Column(length = 50)
    private String loginId;

    @Setter
    @Column(length = 255)
    private String password;
    @Setter
    @Column(length = 10)
    private String name;
    @Setter
    @Column(length = 20)
    private String phoneNumber;

    protected Customer() {
    }

    private Customer(String loginId, String password, String name, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static Customer of(String loginId, String password, String name, String phoneNumber) {
        return new Customer(loginId, password, name, phoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return getLoginId() != null && getLoginId().equals(customer.getLoginId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoginId());
    }
}

package com.honey.reservation.domain.reservation;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.domain.Manager;
import com.honey.reservation.domain.baseentity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@ToString(callSuper = true)
@Getter
@Table(indexes = {
        @Index(columnList = "date"),
        @Index(columnList = "time"),
        @Index(columnList = "reservationStatus"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@Entity
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = LAZY) @JoinColumn(name = "customer_login_id")
    private Customer customer;
    @ManyToOne(optional = false, fetch = LAZY) @JoinColumn(name = "manager_login_id")
    private Manager manager;

    @Setter @Embedded @Column(nullable = false) private ReservationYearDateTime reservationYearDateTime;
    @Setter @Column(length = 1000) private String description;
    @Setter @Enumerated(EnumType.STRING) private ReservationStatus reservationStatus;

    protected Reservation() {}

    private Reservation(
            Customer customer, Manager manager,
            ReservationYearDateTime reservationYearDateTime, String description, ReservationStatus reservationStatus
    ) {
        this.customer = customer;
        this.manager = manager;
        this.reservationYearDateTime = reservationYearDateTime;
        this.description = description;
        this.reservationStatus = reservationStatus;
    }

    private Reservation(
            Long id, Customer customer, Manager manager,
            ReservationYearDateTime reservationYearDateTime, String description, ReservationStatus reservationStatus
    ) {
        this.id = id;
        this.customer = customer;
        this.manager = manager;
        this.reservationYearDateTime = reservationYearDateTime;
        this.description = description;
        this.reservationStatus = reservationStatus;
    }

    public static Reservation of(
            Customer customer, Manager manager,
            ReservationYearDateTime reservationYearDateTime, String description, ReservationStatus reservationStatus
    ) {
        return new Reservation(customer, manager, reservationYearDateTime, description, reservationStatus);
    }

    public static Reservation of(
            Long id, Customer customer, Manager manager,
            ReservationYearDateTime reservationYearDateTime, String description, ReservationStatus reservationStatus
    ) {
        return new Reservation(id, customer, manager, reservationYearDateTime, description, reservationStatus);
    }


}

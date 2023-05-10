package com.honey.reservation.domain.reservation;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.domain.Manager;
import com.honey.reservation.domain.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Reservation extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(optional = false) @JoinColumn(name = "manager_id")
    private Manager manager;

    @Embedded @Column(nullable = false) private ReservationDateTime reservationDateTime;
    @Column(length = 1000) private String description;
    @Enumerated(EnumType.STRING) private ReservationStatus reservationStatus;

    protected Reservation() {}

    private Reservation(
            Customer customer, Manager manager,
            ReservationDateTime reservationDateTime, String description, ReservationStatus reservationStatus
    ) {
        this.customer = customer;
        this.manager = manager;
        this.reservationDateTime = reservationDateTime;
        this.description = description;
        this.reservationStatus = reservationStatus;
    }

    public static Reservation of(
            Customer customer, Manager manager,
            ReservationDateTime reservationDateTime, String description, ReservationStatus reservationStatus
    ) {
        return new Reservation(customer, manager, reservationDateTime, description, reservationStatus);
    }

}

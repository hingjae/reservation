package com.honey.reservation.dto;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.domain.Manager;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationDateTime;
import com.honey.reservation.domain.reservation.ReservationStatus;

public record ReservationDto(
        Long id, CustomerDto customerDto, ManagerDto managerDto,
        ReservationDateTime reservationDateTime, String description, ReservationStatus reservationStatus
) {
    public static ReservationDto of(
            Long id, CustomerDto customerDto, ManagerDto managerDto,
            ReservationDateTime reservationDateTime, String description, ReservationStatus reservationStatus
    ) {
        return new ReservationDto(id, customerDto, managerDto, reservationDateTime, description, reservationStatus);
    }

    public Reservation toEntity(Customer customer, Manager manager) {
        return Reservation.of(customer, manager, reservationDateTime, description, reservationStatus);
    }

    public static ReservationDto from(Reservation entity) {
        return new ReservationDto(
                entity.getId(), CustomerDto.from(entity.getCustomer()), ManagerDto.from(entity.getManager()),
                entity.getReservationDateTime(), entity.getDescription(), entity.getReservationStatus()
        );
    }

}

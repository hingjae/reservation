package com.honey.reservation.dto;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.domain.Manager;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;

public record ReservationDto(
        Long id,
        CustomerDto customerDto,
        ManagerDto managerDto,
        Integer year,
        Integer month,
        Integer day,
        Double time,
        String memo,
        ReservationStatus reservationStatus
) {
    public static ReservationDto of(Long id, CustomerDto customerDto, ManagerDto managerDto, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus) {
        return new ReservationDto(id, customerDto, managerDto, year, month, day, time, memo, reservationStatus);
    }

    public Reservation toEntity(Customer customer, Manager manager) {
        return Reservation.of(customer, manager, year, month, day, time, memo, reservationStatus);
    }

    public static ReservationDto from(Reservation entity) {
        return ReservationDto.of(entity.getId(), CustomerDto.from(entity.getCustomer()), ManagerDto.from(entity.getManager()), entity.getYear(), entity.getMonth(), entity.getDay(), entity.getTime(), entity.getMemo(), entity.getReservationStatus());
    }

}

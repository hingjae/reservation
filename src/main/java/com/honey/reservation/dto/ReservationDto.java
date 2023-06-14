package com.honey.reservation.dto;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;

public record ReservationDto(
        Long id,
        CustomerDto customerDto,
        Integer year,
        Integer month,
        Integer day,
        Double time,
        String memo,
        ReservationStatus reservationStatus
) {
    public static ReservationDto of(Long id, CustomerDto customerDto, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus) {
        return new ReservationDto(id, customerDto, year, month, day, time, memo, reservationStatus);
    }

    public Reservation toEntity(UserAccount customer) {
        return Reservation.of(customer, year, month, day, time, memo, reservationStatus);
    }

    public static ReservationDto from(Reservation entity) {
        return ReservationDto.of(entity.getId(), CustomerDto.from(entity.getCustomer()), entity.getYear(), entity.getMonth(), entity.getDay(), entity.getTime(), entity.getMemo(), entity.getReservationStatus());
    }

}

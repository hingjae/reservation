package com.honey.reservation.dto;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;

public record ReservationDto(
        Long id,
        UserAccountDto userAccountDto,
        Integer year,
        Integer month,
        Integer day,
        Double time,
        String memo,
        ReservationStatus reservationStatus
) {
    public static ReservationDto of(Long id, UserAccountDto userAccountDto, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus) {
        return new ReservationDto(id, userAccountDto, year, month, day, time, memo, reservationStatus);
    }

    public Reservation toEntity(UserAccount userAccount) {
        return Reservation.of(userAccount, year, month, day, time, memo, reservationStatus);
    }

    public static ReservationDto from(Reservation entity) {
        return ReservationDto.of(entity.getId(), UserAccountDto.from(entity.getUserAccount()), entity.getYear(), entity.getMonth(), entity.getDay(), entity.getTime(), entity.getMemo(), entity.getReservationStatus());
    }

}

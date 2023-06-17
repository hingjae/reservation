package com.honey.reservation.dto;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(
        String userId,
        LocalDate localDate,
        LocalTime localTime,
        String memo,
        ReservationStatus reservationStatus
) {
    public static ReservationDto of(UserAccountDto userAccountDto, LocalDate localDate, LocalTime localTime, String memo, ReservationStatus reservationStatus) {
        return new ReservationDto(userAccountDto.loginId(), localDate, localTime, memo, reservationStatus);
    }

    public Reservation toEntity(UserAccount userAccount) {
        return Reservation.of(userAccount, localDate, localTime, memo, reservationStatus);
    }
}

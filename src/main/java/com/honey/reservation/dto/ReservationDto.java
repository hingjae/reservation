package com.honey.reservation.dto;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(
        Long id,
        String userId,
        LocalDate reservationDate,
        LocalTime reservationTime,
        String memo,
        ReservationStatus reservationStatus
) {
    public static ReservationDto of(Long id, UserAccountDto userAccountDto, LocalDate reservationDate, LocalTime reservationTime, String memo, ReservationStatus reservationStatus) {
        return new ReservationDto(id, userAccountDto.loginId(), reservationDate, reservationTime, memo, reservationStatus);
    }

    public static ReservationDto of(UserAccountDto userAccountDto, LocalDate reservationDate, LocalTime reservationTime, String memo, ReservationStatus reservationStatus) {
        return new ReservationDto(null, userAccountDto.loginId(), reservationDate, reservationTime, memo, reservationStatus);
    }

    public static ReservationDto from(Reservation reservation) {
        return ReservationDto.of(
                reservation.getId(),
                UserAccountDto.from(reservation.getUserAccount()),
                reservation.getReservationDate(),
                reservation.getReservationTime(),
                reservation.getMemo(),
                reservation.getReservationStatus()
        );
    }


    public Reservation toEntity(UserAccount userAccount) {
        return Reservation.of(userAccount, reservationDate, reservationTime, memo, reservationStatus);
    }
}

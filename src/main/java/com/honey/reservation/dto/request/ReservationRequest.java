package com.honey.reservation.dto.request;

import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.ReservationDto;
import com.honey.reservation.dto.UserAccountDto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        Long managerId,
        LocalDate localDate,
        LocalTime localTime,
        String memo,
        ReservationStatus reservationStatus
) {
    public static ReservationRequest of(Long managerId, LocalDate localDate, LocalTime localTime, String memo, ReservationStatus reservationStatus) {
        return new ReservationRequest(managerId, localDate, localTime, memo, reservationStatus);
    }

    public ReservationDto toDto(UserAccountDto userAccountDto) {
        return ReservationDto.of(userAccountDto, managerId, localDate, localTime, memo, reservationStatus);
    }
}

package com.honey.reservation.dto.request;

import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.UserAccountDto;
import com.honey.reservation.dto.ReservationDto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(Long id, String userName, Integer year, Integer month, Integer day, Integer hour, Integer minute, String memo, ReservationStatus reservationStatus) {

    public static ReservationRequest of(Long id, String userName, Integer year, Integer month, Integer day, Integer hour, Integer minute, String memo, ReservationStatus reservationStatus) {
        return new ReservationRequest(id, userName, year, month, day, hour, minute, memo, reservationStatus);
    }

    public ReservationDto toDto(UserAccountDto userAccountDto) {
        return ReservationDto.of(id, userAccountDto, LocalDate.of(year, month, day), LocalTime.of(hour, minute), memo, reservationStatus);
    }
}

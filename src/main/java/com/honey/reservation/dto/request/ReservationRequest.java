package com.honey.reservation.dto.request;

import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.UserAccountDto;
import com.honey.reservation.dto.ReservationDto;

public record ReservationRequest(Long id, String userName, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus) {

    public static ReservationRequest of(Long id, String userName, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus) {
        return new ReservationRequest(id, userName, year, month, day, time, memo, reservationStatus);
    }

    public ReservationDto toDto(UserAccountDto userAccountDto) {
        return ReservationDto.of(id, userAccountDto, year, month, day, time, memo, reservationStatus);
    }
}

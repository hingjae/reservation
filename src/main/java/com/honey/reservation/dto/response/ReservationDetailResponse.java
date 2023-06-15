package com.honey.reservation.dto.response;

import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.ReservationDto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDetailResponse(
        Long reservationId,
        LocalDate localDate,
        LocalTime localTime,
        String memo,
        ReservationStatus reservationStatus,
        String userLoginId,
        String username,
        String phoneNumber
) {
    public static ReservationDetailResponse of(Long reservationId, LocalDate localDate, LocalTime localTime, String memo, ReservationStatus reservationStatus, String userLoginId, String username, String phoneNumber) {
        return new ReservationDetailResponse(reservationId, localDate, localTime, memo, reservationStatus, userLoginId, username, phoneNumber);
    }

    public static ReservationDetailResponse from(ReservationDto dto) {
        return ReservationDetailResponse.of(dto.id(), dto.localDate(), dto.localTime(), dto.memo(), dto.reservationStatus(), dto.userAccountDto().loginId(), dto.userAccountDto().name(), dto.userAccountDto().phoneNumber());
    }
}

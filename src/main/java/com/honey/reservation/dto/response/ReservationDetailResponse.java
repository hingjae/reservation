package com.honey.reservation.dto.response;

import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.ReservationDto;

public record ReservationDetailResponse(
        Long reservationId,
        Integer year,
        Integer month,
        Integer day,
        Double time,
        String memo,
        ReservationStatus reservationStatus,
        String userLoginId,
        String username,
        String phoneNumber
) {
    public static ReservationDetailResponse of(Long reservationId, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus, String userLoginId, String username, String phoneNumber) {
        return new ReservationDetailResponse(reservationId, year, month, day, time, memo, reservationStatus, userLoginId, username, phoneNumber);
    }

    public static ReservationDetailResponse from(ReservationDto dto) {
        return ReservationDetailResponse.of(dto.id(), dto.year(), dto.month(), dto.day(), dto.time(), dto.memo(), dto.reservationStatus(), dto.userAccountDto().loginId(), dto.userAccountDto().name(), dto.userAccountDto().phoneNumber());
    }
}

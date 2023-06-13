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
        String customerLoginId,
        String customerName,
        String phoneNumber,
        String managerLoginId,
        String managerName
) {
    public static ReservationDetailResponse of(Long reservationId, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus, String customerLoginId, String customerName, String phoneNumber, String managerLoginId, String managerName) {
        return new ReservationDetailResponse(reservationId, year, month, day, time, memo, reservationStatus, customerLoginId, customerName, phoneNumber, managerLoginId, managerName);
    }

    public static ReservationDetailResponse from(ReservationDto dto) {
        return ReservationDetailResponse.of(dto.id(), dto.year(), dto.month(), dto.day(), dto.time(), dto.memo(), dto.reservationStatus(), dto.customerDto().loginId(), dto.customerDto().name(), dto.customerDto().phoneNumber(), dto.managerDto().loginId(), dto.managerDto().name());
    }
}

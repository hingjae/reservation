package com.honey.reservation.dto.response;

import com.honey.reservation.dto.ReservationDto;

public record ReservationResponse(Long id, Integer year, Integer month, Integer day, Double time, String customerName) {
    public static ReservationResponse of(Long id, Integer year, Integer month, Integer day, Double time, String customerName) {
        return new ReservationResponse(id, year, month, day, time, customerName);
    }

    public static ReservationResponse from(ReservationDto dto) {
        return ReservationResponse.of(dto.id(), dto.year(), dto.month(), dto.day(), dto.time(), dto.customerDto().name());
    }
}

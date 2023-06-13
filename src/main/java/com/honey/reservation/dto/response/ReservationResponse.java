package com.honey.reservation.dto.response;

import com.honey.reservation.dto.ReservationDto;

public record ReservationResponse(Long id, Integer year, Integer month, Integer day, Double time, String customerName, String ManagerName) {
    public static ReservationResponse of(Long id, Integer year, Integer month, Integer day, Double time, String customerName, String ManagerName) {
        return new ReservationResponse(id, year, month, day, time, customerName, ManagerName);
    }

    public static ReservationResponse from(ReservationDto dto) {
        return ReservationResponse.of(dto.id(), dto.year(), dto.month(), dto.day(), dto.time(), dto.customerDto().name(), dto.managerDto().name());
    }
}

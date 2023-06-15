package com.honey.reservation.dto.response;

import com.honey.reservation.dto.ReservationDto;

public record ReservationResponse(Long id, Integer year, Integer month, Integer day, String time, String username) {
    public static ReservationResponse of(Long id, Integer year, Integer month, Integer day, String  time, String username) {
        return new ReservationResponse(id, year, month, day, time, username);
    }

    public static ReservationResponse from(ReservationDto dto) {
        return ReservationResponse.of(dto.id(), dto.localDate().getYear(), dto.localDate().getMonthValue(), dto.localDate().getDayOfMonth(), dto.localTime().toString(), dto.userAccountDto().name());
    }
}

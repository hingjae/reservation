package com.honey.reservation.dto.api;

import java.util.List;

public record UserReservationResponse(
        String name,
        List<ReservationDto> reservations
) {
    public static UserReservationResponse from(String name, List<ReservationDto> dtos) {
        return new UserReservationResponse(name, dtos);
    }
}

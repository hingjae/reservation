package com.honey.reservation.dto.api;

import java.util.List;

public record ReservationListResponse(
        List<ReservationDto> reservations
) {
    public static ReservationListResponse from(List<ReservationDto> reservations) {
        return new ReservationListResponse(reservations);
    }
}

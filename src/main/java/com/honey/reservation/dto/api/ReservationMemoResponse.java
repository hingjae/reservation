package com.honey.reservation.dto.api;

import com.honey.reservation.domain.reservation.Reservation;

public record ReservationMemoResponse(
        String memo
) {
    public static ReservationMemoResponse from(String memo) {
        return new ReservationMemoResponse(memo);
    }
}

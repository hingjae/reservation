package com.honey.reservation.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.honey.reservation.domain.reservation.ReservationStatus;

public record ReservationStatusRequest(
        @JsonProperty("reservationStatus") ReservationStatus reservationStatus
) {
}

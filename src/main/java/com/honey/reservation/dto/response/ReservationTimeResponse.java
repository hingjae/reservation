package com.honey.reservation.dto.response;

import java.time.LocalTime;
import java.util.*;

public record ReservationTimeResponse(
        Map<LocalTime, Boolean> timeButtons
) {

    public static ReservationTimeResponse of(Map<LocalTime, Boolean> map) {
        return new ReservationTimeResponse(map);
    }

    public static ReservationTimeResponse from(Map<LocalTime, Boolean> dto) {
        Map<LocalTime, Boolean> timeButtons = new TreeMap<>(dto);
        return ReservationTimeResponse.of(timeButtons);
    }
}

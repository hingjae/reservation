package com.honey.reservation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public record ReservationTimeResponse(
        Map<LocalTime, Boolean> timeButtons
) {

    public static ReservationTimeResponse of(Map<LocalTime, Boolean> map) {
        return new ReservationTimeResponse(map);
    }

    public static ReservationTimeResponse from(LocalDate reservationDate, Map<LocalTime, Boolean> timeMap) {
        LocalDateTime now = LocalDateTime.now();
        for (Map.Entry<LocalTime, Boolean> entry : timeMap.entrySet()) {
            LocalDateTime localDateTime = LocalDateTime.of(reservationDate, entry.getKey());
            if (localDateTime.isBefore(now)) {
                timeMap.replace(entry.getKey(), false);
            }
        }
        Map<LocalTime, Boolean> timeButtons = new TreeMap<>(timeMap);
        return ReservationTimeResponse.of(timeButtons);
    }
}

package com.honey.reservation.dto.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public record TimesResponse(Map<LocalTime, Boolean> timeBooleanMap) {
    public static TimesResponse from(LocalDate reservationDate, Map<LocalTime, Boolean> map) {
        LocalDateTime now = LocalDateTime.now();
        for (Map.Entry<LocalTime, Boolean> entry : map.entrySet()) {
            LocalDateTime localDateTime = LocalDateTime.of(reservationDate, entry.getKey());
            if (localDateTime.isBefore(now)) {
                map.replace(entry.getKey(), false);
            }
        }
        return new TimesResponse(new TreeMap<>(map));
    }
}

package com.honey.reservation.dto.api;

import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public record TimesResponse(Map<LocalTime, Boolean> timeBooleanMap) {
    public static TimesResponse from(Map<LocalTime, Boolean> map) {
        return new TimesResponse(new TreeMap<>(map));
    }
}

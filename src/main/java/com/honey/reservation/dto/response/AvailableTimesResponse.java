package com.honey.reservation.dto.response;

import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

public record AvailableTimesResponse(
        Map<LocalTime, Boolean> times
) {
    public static AvailableTimesResponse of(Map<LocalTime, Boolean> times) {
        return new AvailableTimesResponse(times);
    }

    public static AvailableTimesResponse from(Map<LocalTime, Boolean> timesDto) {
        return AvailableTimesResponse.of(new TreeMap<>(timesDto));
    }
}

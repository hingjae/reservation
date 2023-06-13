package com.honey.reservation.dto;

import java.time.LocalDateTime;

public record YearDateDto(
         Integer year,
         Integer month,
         Integer day
) {
    public static YearDateDto of(Integer year, Integer month, Integer day) {
        if (year == null && month == null && day == null) {
            year = LocalDateTime.now().getYear();
            month = LocalDateTime.now().getMonthValue();
            day = LocalDateTime.now().getDayOfMonth();
        }
        return new YearDateDto(year, month, day);
    }
}

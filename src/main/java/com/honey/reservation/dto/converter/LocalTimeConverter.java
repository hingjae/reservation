package com.honey.reservation.dto.converter;

import java.time.LocalTime;

public class LocalTimeConverter {
    private static final String DELIMITER = ":";

    public static LocalTime from(String time) {
        String[] hourAndMinutes = time.split(":");
        return LocalTime.of(Integer.parseInt(hourAndMinutes[0]), Integer.parseInt(hourAndMinutes[1]));
    }
}

package com.honey.reservation.domain.reservation;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class ReservationYearDateTime {

    private String year;
    private String date;
    private String time;

    public ReservationYearDateTime(String year, String date, String time) {
        this.year = year;
        this.date = date;
        this.time = time;
    }

    public ReservationYearDateTime() {
    }
}

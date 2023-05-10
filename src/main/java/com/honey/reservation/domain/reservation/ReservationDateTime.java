package com.honey.reservation.domain.reservation;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ReservationDateTime {

    private String date;
    private String time;

    public ReservationDateTime(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public ReservationDateTime() {
    }
}

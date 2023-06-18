package com.honey.reservation.domain.reservation;

import lombok.Getter;

public enum ReservationStatus {
    READY("방문 전"),
    COMP("방문 완료");

    @Getter private final String description;

    ReservationStatus(String description) {
        this.description = description;
    }

    public static boolean isReady(ReservationStatus status) {
        if (status.equals(READY)) {
            return true;
        }
        return false;
    }

}

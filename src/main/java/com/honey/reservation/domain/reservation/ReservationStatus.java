package com.honey.reservation.domain.reservation;

public enum ReservationStatus {
    READY, COMP, CANCEL;

    public static boolean isReady(ReservationStatus status) {
        if (status.equals(READY)) {
            return true;
        }
        return false;
    }

}

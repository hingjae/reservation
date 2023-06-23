package com.honey.reservation.repository.querydsl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepositoryCustom {

    List<LocalTime> findReservationTime(LocalDate reservationDate, Long managerId);

    Long findReservation(Long managerId, LocalDate reservationDate, LocalTime reservationTime);
}

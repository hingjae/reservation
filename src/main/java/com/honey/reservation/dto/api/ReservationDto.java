package com.honey.reservation.dto.api;

import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationDto {
    private Long id;
    private Long managerId;
    private String loginId;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private ReservationStatus reservationStatus;
    private String memo;

    public ReservationDto () {}

    public ReservationDto(Long id, Long managerId, String loginId, LocalDate reservationDate, LocalTime reservationTime, ReservationStatus reservationStatus, String memo) {
        this.id = id;
        this.managerId = managerId;
        this.loginId = loginId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationStatus = reservationStatus;
        this.memo = memo;
    }

    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.managerId = reservation.getManagerAccount().getId();
        this.loginId = reservation.getUserAccount().getLoginId();
        this.reservationDate = reservation.getReservationDate();
        this.reservationTime = reservation.getReservationTime();
        this.reservationStatus = reservation.getReservationStatus();
        this.memo = reservation.getMemo();
    }
}

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
    private String managerName;
    private String loginId;
    private String username;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private ReservationStatus reservationStatus;
    private String memo;

    public ReservationDto () {}

    public ReservationDto(Long id, Long managerId, String managerName, String loginId, String username, LocalDate reservationDate, LocalTime reservationTime, ReservationStatus reservationStatus, String memo) {
        this.id = id;
        this.managerId = managerId;
        this.managerName = managerName;
        this.loginId = loginId;
        this.username = username;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationStatus = reservationStatus;
        this.memo = memo;
    }

    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.managerId = reservation.getManagerAccount().getId();
        this.managerName = reservation.getManagerAccount().getName();
        this.loginId = reservation.getUserAccount().getLoginId();
        this.username = reservation.getUserAccount().getName();
        this.reservationDate = reservation.getReservationDate();
        this.reservationTime = reservation.getReservationTime();
        this.reservationStatus = reservation.getReservationStatus();
        this.memo = reservation.getMemo();
    }
}

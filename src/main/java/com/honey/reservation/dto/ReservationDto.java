package com.honey.reservation.dto;

import com.honey.reservation.domain.ManagerAccount;
import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDto(
        Long id,
        UserAccountDto userAccountDto,
        ManagerAccountDto managerAccountDto,
        LocalDate reservationDate,
        LocalTime reservationTime,
        String memo,
        ReservationStatus reservationStatus
) {
    public static ReservationDto of(Long id, UserAccountDto userAccountDto, ManagerAccountDto managerAccountDto, LocalDate reservationDate, LocalTime reservationTime, String memo, ReservationStatus reservationStatus) {
        return new ReservationDto(id, userAccountDto, managerAccountDto, reservationDate, reservationTime, memo, reservationStatus);
    }

    public static ReservationDto of(UserAccountDto userAccountDto, ManagerAccountDto managerAccountDto, LocalDate reservationDate, LocalTime reservationTime, String memo, ReservationStatus reservationStatus) {
        return ReservationDto.of(null, userAccountDto, managerAccountDto, reservationDate, reservationTime, memo, reservationStatus);
    }

    public static ReservationDto of(UserAccountDto userAccountDto, Long managerId, LocalDate reservationDate, LocalTime reservationTime, String memo, ReservationStatus reservationStatus) {
        return ReservationDto.of(null, userAccountDto, ManagerAccountDto.of(managerId,null), reservationDate, reservationTime, memo, reservationStatus);
    }

    public static ReservationDto from(Reservation reservation) {
        return ReservationDto.of(
                reservation.getId(),
                UserAccountDto.from(reservation.getUserAccount()),
                ManagerAccountDto.from(reservation.getManagerAccount()),
                reservation.getReservationDate(),
                reservation.getReservationTime(),
                reservation.getMemo(),
                reservation.getReservationStatus()
        );
    }


    public Reservation toEntity(UserAccount userAccount, ManagerAccount managerAccount) {
        return Reservation.of(userAccount, managerAccount, reservationDate, reservationTime, memo, reservationStatus);
    }
}

package com.honey.reservation.dto.request;

import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.CustomerDto;
import com.honey.reservation.dto.ManagerDto;
import com.honey.reservation.dto.ReservationDto;

public record ReservationRequest(Long id, String customerName, String ManagerName, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus) {

    public static ReservationRequest of(Long id, String customerName, String ManagerName, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus) {
        return new ReservationRequest(id, customerName, ManagerName, year, month, day, time, memo, reservationStatus);
    }

    public ReservationDto toDto(CustomerDto customerDto, ManagerDto managerDto) {
        return ReservationDto.of(id, customerDto, managerDto, year, month, day, time, memo, reservationStatus);
    }
}

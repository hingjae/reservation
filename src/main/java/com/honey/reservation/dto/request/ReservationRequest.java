package com.honey.reservation.dto.request;

import com.honey.reservation.domain.reservation.ReservationDateTime;
import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.CustomerDto;
import com.honey.reservation.dto.ManagerDto;
import com.honey.reservation.dto.ReservationDto;

public record ReservationRequest(
        Long id, String customerName, String ManagerName, ReservationDateTime reservationDateTime,
        String description, ReservationStatus reservationStatus
) {
    public static ReservationRequest of(
            Long id, String customerName, String ManagerName, ReservationDateTime reservationDateTime,
            String description, ReservationStatus reservationStatus
    ) {
        return new ReservationRequest(id, customerName, ManagerName, reservationDateTime, description, reservationStatus);
    }

    public ReservationDto toDto(CustomerDto customerDto, ManagerDto managerDto) {
        return ReservationDto.of(id, customerDto, managerDto, reservationDateTime, description, reservationStatus);
    }
}

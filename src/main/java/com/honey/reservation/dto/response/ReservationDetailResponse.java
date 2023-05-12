package com.honey.reservation.dto.response;

import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationDateTime;
import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.ReservationDto;

public record ReservationDetailResponse(
        Long reservationId, ReservationDateTime reservationDateTime, String description, ReservationStatus reservationStatus,
        Long customerId, String customerName, String phoneNumber, Long managerId, String managerName
) {
    public static ReservationDetailResponse of(
            Long reservationId, ReservationDateTime reservationDateTime, String description, ReservationStatus reservationStatus,
            Long customerId, String customerName, String phoneNumber, Long managerId, String managerName
    ) {
        return new ReservationDetailResponse(
                reservationId, reservationDateTime, description, reservationStatus,
                customerId, customerName, phoneNumber, managerId, managerName
        );
    }

    public static ReservationDetailResponse from(ReservationDto dto) {
        return ReservationDetailResponse.of(
                dto.id(), dto.reservationDateTime(), dto.description(), dto.reservationStatus(),
                dto.customerDto().id(), dto.customerDto().name(), dto.customerDto().phoneNumber(),
                dto.managerDto().id(), dto.managerDto().name()
        );
    }
}

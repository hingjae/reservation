package com.honey.reservation.dto.response;

import com.honey.reservation.domain.reservation.ReservationYearDateTime;
import com.honey.reservation.dto.ReservationDto;

public record ReservationResponse(
        Long id, ReservationYearDateTime reservationDateTime, String customerName, String ManagerName
) {
    public static ReservationResponse of(
            Long id, ReservationYearDateTime reservationDateTime, String customerName, String ManagerName
    ) {
        return new ReservationResponse(id, reservationDateTime, customerName, ManagerName);
    }

    public static ReservationResponse from(ReservationDto dto) {
        return ReservationResponse.of(
                dto.id(), dto.reservationDateTime(), dto.customerDto().name(), dto.managerDto().name()
        );
    }
}

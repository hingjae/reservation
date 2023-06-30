package com.honey.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateReservationRequest {
    @JsonProperty("managerId")
    private Long managerId;

    @JsonProperty("reservationDate")
    private LocalDate reservationDate;

    @JsonProperty("reservationTime")
    private LocalTime reservationTime;

    public UpdateReservationRequest(Long managerId, LocalDate reservationDate, LocalTime reservationTime) {
        this.managerId = managerId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }
}

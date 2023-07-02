package com.honey.reservation.controller.api;

import com.honey.reservation.dto.api.ReservationListResponse;
import com.honey.reservation.repository.api.ReservationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RequestMapping("/api/search-date")
@RestController
public class SearchDateApiController {

    private final ReservationQueryRepository reservationQueryRepository;

    @GetMapping
    public ReservationListResponse reservations(@RequestParam("reservationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationDate) {
        return ReservationListResponse.from(reservationQueryRepository.findListByReservationDate(reservationDate));
    }
}

package com.honey.reservation.controller;

import com.honey.reservation.dto.YearDateDto;
import com.honey.reservation.dto.response.ReservationDetailResponse;
import com.honey.reservation.dto.response.ReservationResponse;
import com.honey.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@Controller
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public String reservations(
            @PageableDefault(size = 10, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        Page<ReservationResponse> reservations = reservationService.getReservations(pageable)
                .map(ReservationResponse::from);
        map.addAttribute("reservations", reservations);
        return "reservations";
    }

    @GetMapping("/search-date")
    public String searchDate() {
        return "reservations/calendar";
    }

    @GetMapping("/search-date/form")
    public String reservationFrom(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "day", required = false) Integer day,
            ModelMap map
    ) {
        map.addAttribute(
                "times",
                reservationService.availableDateTimeSearch(YearDateDto.of(year, month, day))
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.toList())

        );
        return "reservations/reservation-form";
    }

    @GetMapping("/{reservation-id}")
    public String reservation(@PathVariable("reservation-id") Long reservationId, ModelMap map) {
        ReservationDetailResponse reservation = ReservationDetailResponse
                .from(reservationService.getReservation(reservationId));

        map.addAttribute("reservation", reservation);

        return "reservation";
    }

    @GetMapping("/{reservation-id}/edit")
    public String reservationForm(@PathVariable("reservation-id") Long reservationId, ModelMap map) {
        ReservationDetailResponse reservation = ReservationDetailResponse
                .from(reservationService.getReservation(reservationId));

        map.addAttribute("reservation", reservation);

        return "reservationForm";
    }

    @GetMapping("/form")
    public String reservationForm() {
        return "reservationForm";
    }

}

package com.honey.reservation.controller;

import com.honey.reservation.dto.response.ReservationTimeResponse;
import com.honey.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@RequiredArgsConstructor
@RequestMapping("/reservations")
@Controller
public class ReservationController {

    private final ReservationService reservationService;

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
        LocalDate localDate = LocalDate.of(year, month, day);
        map.addAttribute("timeButtons", ReservationTimeResponse.from(reservationService.availableDateTimeSearch(localDate)).timeButtons());
        map.addAttribute("date", localDate);
        return "reservations/reservation-form";
    }

}

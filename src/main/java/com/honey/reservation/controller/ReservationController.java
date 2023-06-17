package com.honey.reservation.controller;

import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.converter.LocalTimeConverter;
import com.honey.reservation.dto.request.ReservationRequest;
import com.honey.reservation.dto.response.ReservationTimeResponse;
import com.honey.reservation.dto.security.UserAccountUserDetails;
import com.honey.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Slf4j
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
        LocalDate reservationDate = LocalDate.of(year, month, day);
        map.addAttribute("timeButtons", ReservationTimeResponse.from(reservationDate, reservationService.availableDateTimeSearch(reservationDate)).timeButtons());
        map.addAttribute("date", reservationDate);
        return "reservations/reservation-form";
    }

    @PostMapping("/search-date/form")
    public String postReservation(
            int year, int month, int day, String reservationTime, String memo,
            @AuthenticationPrincipal UserAccountUserDetails userAccountUserDetails
    ) {
        ReservationRequest reservationRequest = ReservationRequest.of(LocalDate.of(year, month, day), LocalTimeConverter.from(reservationTime), memo, ReservationStatus.READY);
        reservationService.save(reservationRequest.toDto(userAccountUserDetails.toDto()));
        return "redirect:/";
    }

}

package com.honey.reservation.controller;

import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.ReservationDto;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
        return "redirect:/reservations/my-page";
    }

    @GetMapping("/my-page")
    public String myReservation(@AuthenticationPrincipal UserAccountUserDetails userAccountUserDetails, ModelMap map) {
        List<ReservationDto> reservations = reservationService.findMyReservations(userAccountUserDetails);

        map.addAttribute("name", userAccountUserDetails.name());

        map.addAttribute("reservations", reservations);
        return "reservations/my-page";
    }

    @GetMapping("{reservationId}/search-date")
    public String updateReservationDatePage() {
        return "reservations/update-reservation-calendar";
    }

    @GetMapping("{reservationId}/search-date/form")
    public String updateReservationTimePage(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "day", required = false) Integer day,
            @PathVariable("reservationId") Long reservationId, ModelMap map
    ) {
        LocalDate reservationDate = LocalDate.of(year, month, day);
        map.addAttribute("timeButtons", ReservationTimeResponse.from(reservationDate, reservationService.availableDateTimeSearch(reservationDate)).timeButtons());
        map.addAttribute("date", reservationDate);
        map.addAttribute("reservationId", reservationId);
        return "reservations/update-reservation-form";
    }

    @PostMapping("{reservationId}/update")
    public String updateReservation(
            @PathVariable("reservationId") Long reservationId,
            @AuthenticationPrincipal UserAccountUserDetails userAccountUserDetails,
            int year, int month, int day, String reservationTime, String memo
    ) {
        ReservationRequest reservationRequest = ReservationRequest.of(LocalDate.of(year, month, day), LocalTimeConverter.from(reservationTime), memo, ReservationStatus.READY);

        reservationService.updateReservation(reservationId, reservationRequest.toDto(userAccountUserDetails.toDto()));

        return "redirect:/reservations/my-page";
    }

    @PostMapping("{reservationId}/delete")
    public String deleteReservation(
            @PathVariable("reservationId") Long reservationId,
            @AuthenticationPrincipal UserAccountUserDetails userAccountUserDetails
    ) {
        reservationService.deleteReservation(reservationId, userAccountUserDetails.toDto());
        return "redirect:/reservations/my-page";
    }
}

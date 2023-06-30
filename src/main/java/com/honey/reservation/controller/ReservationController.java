package com.honey.reservation.controller;

import com.honey.reservation.domain.ManagerAccount;
import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.ReservationDto;
import com.honey.reservation.dto.converter.LocalTimeConverter;
import com.honey.reservation.dto.request.ReservationRequest;
import com.honey.reservation.dto.response.ReservationTimeResponse;
import com.honey.reservation.dto.security.UserAccountUserDetails;
import com.honey.reservation.repository.ManagerAccountRepository;
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
    private final ManagerAccountRepository managerAccountRepository;

    @GetMapping("/search-date")
    public String searchDate(ModelMap map) {
        map.addAttribute("managers", managerAccountRepository.findAll());
        return "reservations/calendar";
    }

    @GetMapping("/search-date/form")
    public String reservationFrom(int year, int month, int day, Long managerId, ModelMap map) {
        LocalDate reservationDate = LocalDate.of(year, month, day);
        map.addAttribute("timeButtons", ReservationTimeResponse.from(reservationDate, reservationService.availableDateTimeSearch(reservationDate, managerId)).timeButtons());
        map.addAttribute("date", reservationDate);
        map.addAttribute("managerId", managerId);
        return "reservations/reservation-form";
    }

    @PostMapping("/search-date/form")
    public String postReservation(
            int year, int month, int day, Long managerId, String reservationTime, String memo,
            @AuthenticationPrincipal UserAccountUserDetails userAccountUserDetails
    ) {
        ReservationRequest reservationRequest = ReservationRequest.of(managerId, LocalDate.of(year, month, day), LocalTimeConverter.from(reservationTime), memo, ReservationStatus.READY);
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
    public String updateReservationDatePage(ModelMap map) {
        map.addAttribute("managers", managerAccountRepository.findAll());
        return "reservations/update-reservation-calendar";
    }

    @GetMapping("/{reservationId}/memo")
    public String reservationMemo(
            @AuthenticationPrincipal UserAccountUserDetails userAccountUserDetails,
            @PathVariable Long reservationId, ModelMap map
    ) {
        map.addAttribute("memo", reservationService.findMemo(reservationId, userAccountUserDetails));
        return "reservations/memo";
    }

    @GetMapping("{reservationId}/search-date/form")
    public String updateReservationTimePage(
            int year, int month, int day, Long managerId,
            @PathVariable("reservationId") Long reservationId, ModelMap map
    ) {
        LocalDate reservationDate = LocalDate.of(year, month, day);
        map.addAttribute("timeButtons", ReservationTimeResponse.from(reservationDate, reservationService.availableDateTimeSearch(reservationDate, managerId)).timeButtons());
        map.addAttribute("date", reservationDate);
        map.addAttribute("reservationId", reservationId);
        map.addAttribute("managerId", managerId);
        return "reservations/update-reservation-form";
    }

    @PostMapping("{reservationId}/update")
    public String updateReservation(
            @PathVariable("reservationId") Long reservationId,
            @AuthenticationPrincipal UserAccountUserDetails userAccountUserDetails,
            int year, int month, int day, Long managerId, String reservationTime, String memo
    ) {
        ReservationRequest reservationRequest = ReservationRequest.of(managerId, LocalDate.of(year, month, day), LocalTimeConverter.from(reservationTime), memo, ReservationStatus.READY);

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

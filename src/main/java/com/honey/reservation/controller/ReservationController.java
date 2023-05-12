package com.honey.reservation.controller;

import com.honey.reservation.dto.CustomerDto;
import com.honey.reservation.dto.ReservationDto;
import com.honey.reservation.dto.request.ReservationRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /*
    @PostMapping("/form")
    public String saveReservation(
            ReservationRequest reservationRequest,
            Long customerId, Long managerId
    ) {
        //customer manager 데이터베이스에 접근할 수 있는 서비스로직을 만든 뒤
        //dto를 불러와서 saveReservation 인자에 넣기
        reservationService.saveReservation(reservationRequest.toDto();
        return "redirect:/";
    }
    */
}

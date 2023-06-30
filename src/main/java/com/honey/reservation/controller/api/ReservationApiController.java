package com.honey.reservation.controller.api;

import com.honey.reservation.dto.api.ReservationDto;
import com.honey.reservation.dto.api.TimesResponse;
import com.honey.reservation.dto.request.UpdateReservationRequest;
import com.honey.reservation.repository.ReservationRepository;
import com.honey.reservation.repository.api.ReservationQueryRepository;
import com.honey.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
@RestController
public class ReservationApiController {

    private final ReservationQueryRepository reservationQueryRepository; // query dsl
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @GetMapping
    public Page<ReservationDto> reservations(@PageableDefault(size = 10) Pageable pageable) {
        return reservationRepository.findAll(pageable).map(ReservationDto::new);
    }

    @GetMapping("/{reservationId}")
    public ReservationDto reservation(@PathVariable("reservationId") Long id) {
        return reservationRepository.findById(id)
                .map(ReservationDto::new)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 예약 입니다. - " + id));
    }

    @GetMapping("/time-search")
    public TimesResponse timeSearch(
            @RequestParam Long managerId,
            int year, int month, int day
    ) {
        LocalDate reservationDate = LocalDate.of(year, month, day);
        return TimesResponse.from(reservationService.availableDateTimeSearch(reservationDate, managerId));
    }

    @PutMapping("/{reservationId}")
    public void updateReservation(
            @PathVariable("reservationId") Long reservationId,
            @RequestBody UpdateReservationRequest request
    ) {
        log.info("reservationId : {}", reservationId);
        log.info("request : {}", request);
    }

}
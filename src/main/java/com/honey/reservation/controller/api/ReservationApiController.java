package com.honey.reservation.controller.api;

import com.honey.reservation.domain.ManagerAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.dto.api.*;
import com.honey.reservation.repository.ManagerAccountRepository;
import com.honey.reservation.repository.ReservationRepository;
import com.honey.reservation.repository.api.ReservationQueryRepository;
import com.honey.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private final ManagerAccountRepository managerAccountRepository;

    @GetMapping
    public Page<ReservationDto> reservations(@PageableDefault(size = 20) Pageable pageable) {
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
        return TimesResponse.from(reservationDate, reservationService.availableDateTimeSearch(reservationDate, managerId));
    }

    @GetMapping("/{reservationId}/memo")
    public ReservationMemoResponse getReservationMemo(@PathVariable("reservationId")Long reservationId) {
        return ReservationMemoResponse.from(reservationRepository.findMemoById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 예약 입니다. - " + reservationId)));
    }


    @Transactional
    @PutMapping("/{reservationId}")
    public ResponseEntity<String> updateReservation(
            @PathVariable("reservationId") Long reservationId, @RequestBody UpdateReservationRequest request
    ) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 reservation"));
        ManagerAccount managerAccount = managerAccountRepository.getReferenceById(request.getManagerId());
        reservation.setManagerAccount(managerAccount);
        reservation.setReservationDate(request.getReservationDate());
        reservation.setReservationTime(request.getReservationTime());

        return ResponseEntity.ok("success");
    }

    @Transactional
    @PutMapping("/{reservationId}/complete")
    public void updateReservationStatus(
            @PathVariable("reservationId")Long reservationId, @RequestBody ReservationStatusRequest request
    ) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 reservation"));
        reservation.setReservationStatus(request.reservationStatus());
        reservationRepository.flush();
    }

    @Transactional
    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") Long id) {
        reservationRepository.deleteById(id);
    }


}
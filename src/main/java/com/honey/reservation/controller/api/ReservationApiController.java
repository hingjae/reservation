package com.honey.reservation.controller.api;

import com.honey.reservation.dto.api.ReservationDto;
import com.honey.reservation.repository.api.ReservationApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReservationApiController {

    private final ReservationApiRepository reservationApiRepository;


    @GetMapping("/reservations")
    public Page<ReservationDto> reservations(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return reservationApiRepository.findAllDateTimeDesc(pageable);
    }

}
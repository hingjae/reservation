package com.honey.reservation.service;

import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.repository.ReservationRepository;
import com.honey.reservation.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserAccountRepository userAccountRepository;

    public static final LocalTime START_TIME = LocalTime.of(9, 0);
    public static final LocalTime END_TIME = LocalTime.of(17, 30);
    public static final int INTERVAL_MINUTES = 30;

    @Transactional(readOnly = true)
    public Map<LocalTime, Boolean> availableDateTimeSearch(LocalDate localDate) {
        //메서드가 실행될 때마다 Map을 갱신해야함. 생성자로?
        Map<LocalTime, Boolean> times = Stream.iterate(START_TIME, time -> time.plusMinutes(INTERVAL_MINUTES))
                .limit((END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay()) / (INTERVAL_MINUTES * 60) + 1)
                .collect(Collectors.toMap(time -> time, time -> true));

        reservationRepository.findByLocalDate(localDate).stream()
                .map(Reservation::getLocalTime)
                .forEach(time -> times.replace(time, false));

        return times;
    }

}

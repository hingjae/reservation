package com.honey.reservation.service;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.ReservationDto;
import com.honey.reservation.dto.request.ReservationRequest;
import com.honey.reservation.repository.ReservationRepository;
import com.honey.reservation.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Map<LocalTime, Boolean> times = Stream.iterate(START_TIME, time -> time.plusMinutes(INTERVAL_MINUTES))
                .limit((END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay()) / (INTERVAL_MINUTES * 60) + 1)
                .collect(Collectors.toMap(time -> time, time -> true));

        reservationRepository.findByLocalDate(localDate).stream()
                .map(Reservation::getLocalTime)
                .forEach(time -> times.replace(time, false));


        return times;
    }

    public void save(ReservationDto dto) {
        validateDuplicateReservation(dto);
        UserAccount userAccount = userAccountRepository.findById(dto.userId()).orElseThrow(() -> new EntityNotFoundException("없는 계정입니다."));
        validateReservationCount(userAccount);
        validateReservationDateTime(dto.localDate(), dto.localTime());
        reservationRepository.save(dto.toEntity(userAccount));
    }

    private void validateReservationDateTime(LocalDate reservationDate, LocalTime reservationTime) {
        LocalDateTime reservationLocalDateTime = LocalDateTime.of(reservationDate, reservationTime);
        if (reservationLocalDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("이미 지난 날짜/시간은 예약할 수 없습니다.");
        }
    }

    private void validateReservationCount(UserAccount userAccount) {
        List<ReservationStatus> readyReservations = userAccount.getReservations().stream()
                .map(Reservation::getReservationStatus)
                .filter(ReservationStatus::isReady)
                .collect(Collectors.toList());
        if (readyReservations.size() >= 1) {
            throw new IllegalStateException("예약은 1회만 가능합니다");
        }
    }

    private void validateDuplicateReservation(ReservationDto dto) {
        if (reservationRepository.findByLocalDateAndLocalTime(dto.localDate(), dto.localTime()).isPresent()) {
            throw new IllegalStateException("이미 예약이 있습니다.");
        }
    }


}

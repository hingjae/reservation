package com.honey.reservation.service;

import com.honey.reservation.domain.ManagerAccount;
import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.ReservationDto;
import com.honey.reservation.dto.UserAccountDto;
import com.honey.reservation.dto.security.UserAccountUserDetails;
import com.honey.reservation.repository.ManagerAccountRepository;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserAccountRepository userAccountRepository;
    private final ManagerAccountRepository managerAccountRepository;

    public static final LocalTime START_TIME = LocalTime.of(9, 0);
    public static final LocalTime END_TIME = LocalTime.of(17, 30);
    public static final int INTERVAL_MINUTES = 30;

    @Transactional(readOnly = true)
    public Map<LocalTime, Boolean> availableDateTimeSearch(LocalDate localDate, Long managerId) {
        Map<LocalTime, Boolean> times = generateTimes();
        /*
        reservationRepository.findByReservationDate(localDate).stream()
                .map(Reservation::getReservationTime)
                .forEach(time -> times.replace(time, false));
                */
        reservationRepository.findReservationTime(localDate, managerId).stream()
                .forEach(time -> times.replace(time, false));

        return times;
    }

    private static Map<LocalTime, Boolean> generateTimes() {
        return Stream.iterate(START_TIME, time -> time.plusMinutes(INTERVAL_MINUTES))
                .limit((END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay()) / (INTERVAL_MINUTES * 60) + 1)
                .collect(Collectors.toMap(time -> time, time -> true));
    }

    public void save(ReservationDto dto) {
        validateDuplicateReservation(dto);
        UserAccount userAccount = userAccountRepository.findById(dto.userAccountDto().loginId()).orElseThrow(() -> new EntityNotFoundException("없는 계정입니다."));
        ManagerAccount managerAccount = managerAccountRepository.findById(dto.managerAccountDto().id()).orElseThrow(() -> new EntityNotFoundException("없는 메니저 아이디 입니다."));
        validateReservationCount(userAccount);
        validateReservationDateTime(dto.reservationDate(), dto.reservationTime());
        reservationRepository.save(dto.toEntity(userAccount, managerAccount));
    }

    @Transactional(readOnly = true)
    public List<ReservationDto> findMyReservations(UserAccountUserDetails userDetails) {
        UserAccount userAccount = userAccountRepository.findById(userDetails.getUsername()).orElseThrow(() -> new EntityNotFoundException("없는 계정입니다."));
        return userAccount.getReservations().stream()
                .sorted(comparing(Reservation::getReservationDate)
                        .thenComparing(Reservation::getReservationTime)
                        .reversed())
                .map(ReservationDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public String findMemo(Long reservationId, UserAccountUserDetails userAccountUserDetails) {
        Reservation reservation = reservationRepository.getReferenceById(reservationId);
        UserAccount userAccount = userAccountRepository.getReferenceById(userAccountUserDetails.getUsername());
        if (reservation.getUserAccount().equals(userAccount)) {
            String memo = reservationRepository.findMemoById(reservationId)
                    .orElse("memo");
            log.info("memo : {}", memo);
            return memo;
        }
        throw new IllegalStateException("접근할 수 없습니다.");
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
        reservationRepository.findReservation(dto.managerAccountDto().id(), dto.reservationDate(), dto.reservationTime());
    }

    public void updateReservation(Long reservationId, ReservationDto dto) {
        Reservation reservation = reservationRepository.getReferenceById(reservationId);
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().loginId());
        ManagerAccount managerAccount = managerAccountRepository.findById(dto.managerAccountDto().id()).orElseThrow(() -> new EntityNotFoundException("없는 메니저 입니다."));
        if (reservation.getUserAccount().equals(userAccount)) {
            if (dto.reservationDate() != null && dto.reservationTime() != null) {
                reservation.setManagerAccount(managerAccount);
                reservation.setReservationDate(dto.reservationDate());
                reservation.setReservationTime(dto.reservationTime());
            }
            reservation.setMemo(dto.memo());
            reservation.setReservationStatus(dto.reservationStatus());
            reservationRepository.flush();
        }
    }

    public void deleteReservation(Long reservationId, UserAccountDto toDto) {
        Reservation reservation = reservationRepository.getReferenceById(reservationId);
        UserAccount userAccount = userAccountRepository.getReferenceById(toDto.loginId());
        if (reservation.getUserAccount().equals(userAccount)) {
            reservationRepository.delete(reservation);
        }
    }
}

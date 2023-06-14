package com.honey.reservation.service;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.dto.ReservationDto;
import com.honey.reservation.dto.YearDateDto;
import com.honey.reservation.repository.CustomerRepository;
import com.honey.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static com.honey.reservation.domain.reservation.ReservationStatus.CANCEL;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public Page<ReservationDto> getReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable).map(ReservationDto::from);
    }

    @Transactional(readOnly = true)
    public List<Double> availableDateTimeSearch(YearDateDto dto) {
        List<Double> times = DoubleStream.iterate(9, n -> n + 0.5)
                .limit(18)
                .boxed()
                .collect(Collectors.toList());
        reservationRepository.findByYearAndMonthAndDay(dto.year(), dto.month(), dto.day()).stream()
                .map(Reservation::getTime)
                .forEach(time -> times.remove(time));
        return times;
    }

    @Transactional(readOnly = true)
    public ReservationDto getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(ReservationDto::from)
                .orElseThrow(() -> new IllegalArgumentException("예약이 없습니다."));
    }

    public void saveReservation(ReservationDto dto) {
        UserAccount customer = customerRepository.getReferenceById(dto.customerDto().loginId());

        Reservation reservation = dto.toEntity(customer);
        reservationRepository.save(reservation);
    }

    // TODO: Request를 변환한 dto이므로 dto에 인증정보가 담겨있어야 함.
    public void updateReservation(Long reservationId, ReservationDto dto) {
        try {
            Reservation reservation = reservationRepository.getReferenceById(reservationId);
            UserAccount customer = customerRepository.getReferenceById(dto.customerDto().loginId());
            updateReservationDateTime(dto, reservation, customer);
        } catch (EntityNotFoundException e) {
            log.warn("예약을 찾을 수 없습니다. {}", dto);
        }
    }

    public void cancelReservation(Long reservationId, ReservationDto dto) {
        try {
            Reservation reservation = reservationRepository.getReferenceById(reservationId);
            UserAccount customer = customerRepository.getReferenceById(dto.customerDto().loginId());
            if (reservation.getCustomer().equals(customer)) {
                reservation.setReservationStatus(CANCEL);
            }
        } catch (EntityNotFoundException e) {
            log.warn("예약을 찾을 수 없습니다.", dto);
        }
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    private static void updateReservationDateTime(ReservationDto dto, Reservation reservation, UserAccount customer) {
        if (reservation.getCustomer().equals(customer)) {
            if (dto.year() != null && dto.month() != null && dto.day() != null && dto.time() != null) {
                reservation.setYear(dto.year());
                reservation.setMonth(dto.month());
                reservation.setDay(dto.day());
                reservation.setTime(dto.time());
            }
        }
    }
}

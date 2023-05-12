package com.honey.reservation.service;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.domain.Manager;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.dto.ReservationDto;
import com.honey.reservation.repository.CustomerRepository;
import com.honey.reservation.repository.ManagerRepository;
import com.honey.reservation.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.honey.reservation.domain.reservation.ReservationStatus.CANCEL;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;

    @Transactional(readOnly = true)
    public Page<ReservationDto> getReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable).map(ReservationDto::from);
    }

    public void saveReservation(ReservationDto dto) {
        Customer customer = customerRepository.getReferenceById(dto.customerDto().id());
        Manager manager = managerRepository.getReferenceById(dto.managerDto().id());

        Reservation reservation = dto.toEntity(customer, manager);
        reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public ReservationDto getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(ReservationDto::from)
                .orElseThrow(() -> new IllegalArgumentException("예약이 없습니다."));
    }

    // TODO: Request를 변환한 dto이므로 dto에 인증정보가 담겨있어야 함.
    public void updateReservation(Long reservationId, ReservationDto dto) {
        try {
            Reservation reservation = reservationRepository.getReferenceById(reservationId);
            Customer customer = customerRepository.getReferenceById(dto.customerDto().id());
            updateReservationDateTime(dto, reservation, customer);
        } catch (EntityNotFoundException e) {
            log.warn("예약을 찾을 수 없습니다. {}", dto);
        }
    }

    public void cancelReservation(Long reservationId, ReservationDto dto) {
        try {
            Reservation reservation = reservationRepository.getReferenceById(reservationId);
            Customer customer = customerRepository.getReferenceById(dto.customerDto().id());
            if (reservation.getCustomer().equals(customer)) {
                reservation.setReservationStatus(CANCEL);
            }
        } catch (EntityNotFoundException e) {
            log.warn("예약을 찾을 수 없습니다.", dto);
        }
    }

    public void deleteReservation(Long reservationId, Long managerId) {
        reservationRepository.deleteByIdAndManager_id(reservationId, managerId);
    }

    private static void updateReservationDateTime(ReservationDto dto, Reservation reservation, Customer customer) {
        if (reservation.getCustomer().equals(customer)) {
            if (dto.reservationDateTime() != null) {
                reservation.setReservationDateTime(dto.reservationDateTime());
            }
        }
    }

}

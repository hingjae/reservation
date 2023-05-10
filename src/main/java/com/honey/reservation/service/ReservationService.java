package com.honey.reservation.service;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.domain.Manager;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.dto.ReservationDto;
import com.honey.reservation.repository.CustomerRepository;
import com.honey.reservation.repository.ManagerRepository;
import com.honey.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final ManagerRepository managerRepository;

    public void saveReservation(ReservationDto dto) {
        Customer customer = customerRepository.getReferenceById(dto.customerDto().id());
        Manager manager = managerRepository.getReferenceById(dto.managerDto().id());

        Reservation reservation = dto.toEntity(customer, manager);
        reservationRepository.save(reservation);
    }

    public ReservationDto getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(ReservationDto::from)
                .orElseThrow(() -> new IllegalArgumentException("예약이 없습니다."));
    }

    /*
    // TODO: Request를 변환한 dto이므로 dto에 인증정보가 담겨있어야 함.
    public void updateReservation(Long reservationId, ReservationDto dto) {
        Reservation reservation = reservationRepository.getReferenceById(reservationId);
        Customer customer = customerRepository.getReferenceById(dto.customerDto().id());

        if (reservation.getCustomer().equals(customer)) {
            if()
        }
    }
    */
}

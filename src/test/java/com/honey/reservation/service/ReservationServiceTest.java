package com.honey.reservation.service;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.domain.Manager;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationDateTime;
import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.CustomerDto;
import com.honey.reservation.dto.ManagerDto;
import com.honey.reservation.dto.ReservationDto;
import com.honey.reservation.repository.CustomerRepository;
import com.honey.reservation.repository.ManagerRepository;
import com.honey.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 예약 서비스")
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks ReservationService sut;
    @Mock ReservationRepository reservationRepository;
    @Mock CustomerRepository customerRepository;
    @Mock ManagerRepository managerRepository;

    @DisplayName("예약 저장")
    @Test
    void saveReservation() {
        ReservationDto reservationDto = createReservationDto();

        given(customerRepository.getReferenceById(reservationDto.customerDto().id())).willReturn(createCustomer());
        given(managerRepository.getReferenceById(reservationDto.managerDto().id())).willReturn(createManager());
        given(reservationRepository.save(any(Reservation.class))).willReturn(createReservation());

        sut.saveReservation(reservationDto);

        then(customerRepository).should().getReferenceById(reservationDto.customerDto().id());
        then(managerRepository).should().getReferenceById(reservationDto.managerDto().id());
        then(reservationRepository).should().save(any(Reservation.class));
    }

    @DisplayName("예약 찾기")
    @Test
    void findReservation() {
        Long reservationId = 1L;
        Reservation reservation = createReservation();
        
        given(reservationRepository.findById(reservationId)).willReturn(Optional.of(reservation));

        ReservationDto findReservationDto = sut.getReservation(reservationId);

        assertThat(findReservationDto).isEqualTo(ReservationDto.from(reservation));
        then(reservationRepository).should().findById(reservationId);
    }

    @DisplayName("예약 수정")
    @Test
    void updateReservation() {

    }

    private Customer createCustomer() {
        return Customer.of(1L, null, null, "customer", "phoneNumber");
    }

    private CustomerDto createCustomerDto() {
        return CustomerDto.of(1L, "customer", "phoneNumber");
    }

    private Manager createManager() {
        return Manager.of(1L, null, null, "manager");
    }

    private ManagerDto createManagerDto() {
        return ManagerDto.of(1L, "manager");
    }

    private Reservation createReservation() {
        return Reservation.of(
                1L, createCustomer(), createManager(),
                createReservationDateTime(), "description", ReservationStatus.READY
        );
    }

    private ReservationDto createReservationDto() {
        return ReservationDto.of(
                1L, createCustomerDto(), createManagerDto(),
                createReservationDateTime(), "description", ReservationStatus.READY
        );
    }

    private ReservationDateTime createReservationDateTime() {
        return new ReservationDateTime("230101", "0000");
    }
}
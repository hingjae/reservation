package com.honey.reservation.service;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.domain.Manager;
import com.honey.reservation.domain.reservation.Reservation;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

        given(customerRepository.getReferenceById(reservationDto.customerDto().loginId())).willReturn(createCustomer());
        given(managerRepository.getReferenceById(reservationDto.managerDto().loginId())).willReturn(createManager());
        given(reservationRepository.save(any(Reservation.class))).willReturn(createReservation());

        sut.saveReservation(reservationDto);

        then(customerRepository).should().getReferenceById(reservationDto.customerDto().loginId());
        then(managerRepository).should().getReferenceById(reservationDto.managerDto().loginId());
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
        Reservation reservation = createReservation();
        Customer customer = createCustomer();
        ReservationDto reservationDto = createReservationDto();

        given(reservationRepository.getReferenceById(reservationDto.id())).willReturn(reservation);
        given(customerRepository.getReferenceById(reservationDto.customerDto().loginId())).willReturn(customer);

        sut.updateReservation(reservation.getId(), reservationDto);

        then(reservationRepository).should().getReferenceById(reservationDto.id());
        then(customerRepository).should().getReferenceById(reservationDto.customerDto().loginId());

    }

    @DisplayName("예약 취소")
    @Test
    void cancelReservation() {
        Reservation reservation = createReservation();
        Customer customer = createCustomer();
        ReservationDto reservationDto = createReservationDto();
        given(reservationRepository.getReferenceById(reservationDto.id())).willReturn(reservation);
        given(customerRepository.getReferenceById(reservationDto.customerDto().loginId())).willReturn(customer);

        sut.cancelReservation(reservation.getId(), reservationDto);

        then(reservationRepository).should().getReferenceById(reservationDto.id());
        then(customerRepository).should().getReferenceById(reservationDto.customerDto().loginId());
    }


    private Customer createCustomer() {
        return Customer.of("loginId", null, "customer", "phoneNumber");
    }

    private CustomerDto createCustomerDto() {
        return CustomerDto.of("loginId", "password", "name", "phoneNumber");
    }

    private Manager createManager() {
        return Manager.of("loginId", null, "manager");
    }

    private ManagerDto createManagerDto() {
        return ManagerDto.of("loginId", "manager");
    }

    private Reservation createReservation() {
        return Reservation.of(
                1L, createCustomer(), createManager(),
                2023, 12, 4, 3.0, "memo", ReservationStatus.READY
        );
    }

    private ReservationDto createReservationDto() {
        return ReservationDto.of(
                1L, createCustomerDto(), createManagerDto(),
                2023, 12, 5, 10.0, "description", ReservationStatus.READY
        );
    }

}
package com.honey.reservation.service;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.UserAccountDto;
import com.honey.reservation.repository.UserAccountRepository;
import com.honey.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @Mock
    UserAccountRepository userAccountRepository;

    @DisplayName("예약 저장")
    @Test
    void saveReservation() {


    }

    @DisplayName("예약 찾기")
    @Test
    void findReservation() {

    }

    @DisplayName("예약 수정")
    @Test
    void updateReservation() {

    }

    @DisplayName("예약 취소")
    @Test
    void cancelReservation() {

    }


    private UserAccount createUserAccount() {
        return UserAccount.of("loginId", null, "user", "phoneNumber");
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of("loginId", "password", "name", "phoneNumber");
    }


    private Reservation createReservation() {
        return Reservation.of(
                1L, createUserAccount(), LocalDate.of(2023, 12, 4), LocalTime.of(15, 0), "memo", ReservationStatus.READY
        );
    }

}
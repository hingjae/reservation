package com.honey.reservation.service;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.UserAccountDto;
import com.honey.reservation.dto.ReservationDto;
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
        ReservationDto reservationDto = createReservationDto();

        given(userAccountRepository.getReferenceById(reservationDto.userAccountDto().loginId())).willReturn(createUserAccount());
        given(reservationRepository.save(any(Reservation.class))).willReturn(createReservation());

        sut.saveReservation(reservationDto);

        then(userAccountRepository).should().getReferenceById(reservationDto.userAccountDto().loginId());
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
        UserAccount userAccount = createUserAccount();
        ReservationDto reservationDto = createReservationDto();

        given(reservationRepository.getReferenceById(reservationDto.id())).willReturn(reservation);
        given(userAccountRepository.getReferenceById(reservationDto.userAccountDto().loginId())).willReturn(userAccount);

        sut.updateReservation(reservation.getId(), reservationDto);

        then(reservationRepository).should().getReferenceById(reservationDto.id());
        then(userAccountRepository).should().getReferenceById(reservationDto.userAccountDto().loginId());

    }

    @DisplayName("예약 취소")
    @Test
    void cancelReservation() {
        Reservation reservation = createReservation();
        UserAccount userAccount = createUserAccount();
        ReservationDto reservationDto = createReservationDto();
        given(reservationRepository.getReferenceById(reservationDto.id())).willReturn(reservation);
        given(userAccountRepository.getReferenceById(reservationDto.userAccountDto().loginId())).willReturn(userAccount);

        sut.cancelReservation(reservation.getId(), reservationDto);

        then(reservationRepository).should().getReferenceById(reservationDto.id());
        then(userAccountRepository).should().getReferenceById(reservationDto.userAccountDto().loginId());
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

    private ReservationDto createReservationDto() {
        return ReservationDto.of(
                1L, createUserAccountDto(), LocalDate.of(2023, 12, 5), LocalTime.of(10, 0), "description", ReservationStatus.READY
        );
    }

}
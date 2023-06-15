package com.honey.reservation.repository;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Transactional
@SpringBootTest
class ReservationRepositoryTest {

    @Autowired ReservationRepository reservationRepository;
    @Autowired UserAccountRepository userAccountRepository;

    @Test
    void save() {
        UserAccount userAccount = UserAccount.of("loginId1", "pw", "userAccount", null);
        userAccountRepository.save(userAccount);
        Reservation reservation = Reservation.of(userAccount, LocalDate.of(2023, 6, 13), LocalTime.of(15, 0), "description", ReservationStatus.READY);
        reservationRepository.save(reservation);
    }
}
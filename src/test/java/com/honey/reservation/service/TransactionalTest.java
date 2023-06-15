package com.honey.reservation.service;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.repository.ReservationRepository;
import com.honey.reservation.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class TransactionalTest {

    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserAccountRepository userAccountRepository;

    public static final LocalTime START_TIME = LocalTime.of(9, 0);
    public static final LocalTime END_TIME = LocalTime.of(17, 30);
    public static final int INTERVAL_MINUTES = 30;

    @DisplayName("예약 가능 시간 검색")
    @Test
    void givenYearMonthDay_whenSearch_thenReturnAvailableTime() {
        UserAccount Useraccount = createUserAccount();

        userAccountRepository.saveAndFlush(Useraccount);


        Reservation reservation1 = Reservation.of(Useraccount, LocalDate.of(2023, 6, 15), LocalTime.of(10, 0), "memo", ReservationStatus.READY);
        Reservation reservation2 = Reservation.of(Useraccount, LocalDate.of(2023, 6, 15), LocalTime.of(10, 30), "memo", ReservationStatus.READY);
        Reservation reservation3 = Reservation.of(Useraccount, LocalDate.of(2023, 6, 15), LocalTime.of(11, 0), "memo", ReservationStatus.READY);
        Reservation reservation4 = Reservation.of(Useraccount, LocalDate.of(2023, 6, 15), LocalTime.of(11, 30), "memo", ReservationStatus.READY);


        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);
        reservationRepository.save(reservation4);
        reservationRepository.flush();

        LocalDate localDate = LocalDate.of(2023, 6, 15);
        Map<LocalTime, Boolean> times = reservationService.availableDateTimeSearch(localDate);
        System.out.println("times = " + times);

    }


    private UserAccount createUserAccount() {
        return UserAccount.of("Useraccount1", "pw1", "Useraccount1", "1234");
    }
}

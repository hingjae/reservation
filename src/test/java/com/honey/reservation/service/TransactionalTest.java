package com.honey.reservation.service;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;
import com.honey.reservation.dto.YearDateDto;
import com.honey.reservation.repository.UserAccountRepository;
import com.honey.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class TransactionalTest {

    @Autowired ReservationService reservationService;
    @Autowired ReservationRepository reservationRepository;
    @Autowired UserAccountRepository userAccountRepository;

    @DisplayName("예약 가능 시간 검색")
    @Test
    void givenYearMonthDay_whenSearch_thenReturnAvailableTime() {
        UserAccount Useraccount = createUserAccount();

        userAccountRepository.saveAndFlush(Useraccount);


        Reservation reservation1 = Reservation.of(Useraccount, 2023, 6, 13, 10.0, "memo", ReservationStatus.READY);
        Reservation reservation2 = Reservation.of(Useraccount, 2023, 6, 13, 10.5, "memo", ReservationStatus.READY);
        Reservation reservation3 = Reservation.of(Useraccount, 2023, 6, 13, 11.0, "memo", ReservationStatus.READY);
        Reservation reservation4 = Reservation.of(Useraccount, 2023, 6, 13, 11.5, "memo", ReservationStatus.READY);


        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);
        reservationRepository.save(reservation4);
        reservationRepository.flush();

        YearDateDto dto = YearDateDto.of(2023, 6, 13);
        List<Double> availableTimes = reservationService.availableDateTimeSearch(dto);
        assertThat(availableTimes).isEqualTo(List.of(
            9.0, 9.5, 12.0, 12.5, 13.0, 13.5, 14.0, 14.5, 15.0, 15.5, 16.0, 16.5, 17.0, 17.5
        ));
        List<String> collect = availableTimes.stream().map(String::valueOf).collect(Collectors.toList());
        System.out.println("collect = " + collect);
    }


    private UserAccount createUserAccount() {
        return UserAccount.of("Useraccount1", "pw1", "Useraccount1", "1234");
    }
}

package com.honey.reservation.repository;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ReservationRepositoryTest {

    @Autowired ReservationRepository reservationRepository;
    @Autowired CustomerRepository customerRepository;

    @Test
    void save() {
        UserAccount customer = UserAccount.of("loginId1", "pw", "customer", null);
        customerRepository.save(customer);
        Reservation reservation = Reservation.of(customer, 2023, 6, 13, 15.0, "description", ReservationStatus.READY);
        reservationRepository.save(reservation);
    }
}
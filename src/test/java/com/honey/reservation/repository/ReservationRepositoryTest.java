package com.honey.reservation.repository;

import com.honey.reservation.domain.*;
import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.domain.reservation.ReservationDateTime;
import com.honey.reservation.domain.reservation.ReservationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ReservationRepositoryTest {

    @Autowired ReservationRepository reservationRepository;
    @Autowired CustomerRepository customerRepository;
    @Autowired ManagerRepository managerRepository;

    @Commit
    @Test
    void save() {
        Customer customer = Customer.of("loginId1", "pw", "customer", null);
        Manager manager = Manager.of("loginId2", "pw", "manager");
        customerRepository.save(customer);
        managerRepository.save(manager);
        ReservationDateTime reservationDateTime = new ReservationDateTime("01", "13:30");
        Reservation reservation = Reservation.of(customer, manager, reservationDateTime, "description", ReservationStatus.READY);
        reservationRepository.save(reservation);
    }
}
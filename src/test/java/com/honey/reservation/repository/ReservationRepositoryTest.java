package com.honey.reservation.repository;

import com.honey.reservation.domain.Customer;
import com.honey.reservation.domain.Manager;
import com.honey.reservation.domain.reservation.Reservation;
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
        Reservation reservation = Reservation.of(customer, manager, 2023, 6, 13, 15.0, "description", ReservationStatus.READY);
        reservationRepository.save(reservation);
    }
}
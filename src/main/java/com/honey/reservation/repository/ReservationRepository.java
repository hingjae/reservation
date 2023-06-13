package com.honey.reservation.repository;

import com.honey.reservation.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    void deleteByIdAndManager_loginId(Long reservationId, String managerLoginId);

    List<Reservation> findByYearAndMonthAndDay(Integer year, Integer month, Integer day); // 쿼리dsl로 바꿔볼 것을 고려.
}

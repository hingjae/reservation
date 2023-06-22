package com.honey.reservation.repository;

import com.honey.reservation.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    //    List<Reservation> findByYearAndMonthAndDay(Integer year, Integer month, Integer day); // 쿼리dsl로 바꿔볼 것을 고려.
    List<Reservation> findByReservationDate(LocalDate reservationDate);

    Optional<Reservation> findByReservationDateAndReservationTime(LocalDate reservationDate, LocalTime reservationTime);

    @Query("select r.memo from Reservation r where r.id = :id")
    Optional<String> findMemoById(@Param("id") Long id);
}

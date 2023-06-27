package com.honey.reservation.repository;

import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.repository.querydsl.ReservationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    @Query("select r.memo from Reservation r where r.id = :id")
    Optional<String> findMemoById(@Param("id") Long id);
}

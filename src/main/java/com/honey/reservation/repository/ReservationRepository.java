package com.honey.reservation.repository;

import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.repository.querydsl.ReservationRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    @Query("select r.memo from Reservation r where r.id = :id")
    Optional<String> findMemoById(@Param("id") Long id);

    @Override
    @EntityGraph(attributePaths = {"userAccount", "managerAccount"})
    Page<Reservation> findAll(Pageable pageable);
}

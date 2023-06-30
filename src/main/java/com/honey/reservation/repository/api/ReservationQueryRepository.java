package com.honey.reservation.repository.api;

import com.honey.reservation.dto.api.ReservationDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.honey.reservation.domain.QManagerAccount.managerAccount;
import static com.honey.reservation.domain.QUserAccount.userAccount;
import static com.honey.reservation.domain.reservation.QReservation.reservation;

@Repository
public class ReservationQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ReservationQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ReservationDto> findAll() {
        return queryFactory
                .select(Projections.constructor(ReservationDto.class,
                        reservation.id, managerAccount.id, userAccount.loginId, reservation.reservationDate, reservation.reservationTime, reservation.reservationStatus, reservation.memo
                ))
                .from(reservation)
                .join(reservation.managerAccount, managerAccount)
                .join(reservation.userAccount, userAccount)
                .orderBy(reservation.id.desc())
                .fetch();
    }

    public Page<ReservationDto> findAllDateTimeDesc(Pageable pageable) {
        List<ReservationDto> contents = queryFactory
                .select(Projections.constructor(
                        ReservationDto.class,
                        reservation.id, managerAccount.id, userAccount.loginId, reservation.reservationDate, reservation.reservationTime, reservation.reservationStatus, reservation.memo
                ))
                .from(reservation)
                .join(reservation.managerAccount, managerAccount)
                .join(reservation.userAccount, userAccount)
                .orderBy(reservation.reservationDate.desc(), reservation.reservationTime.desc())
                .fetch();

        long totalElements = queryFactory
                .select(reservation.count())
                .from(reservation)
                .fetchOne();

        return new PageImpl<>(contents, pageable, totalElements);
    }

    public List<LocalTime> findReservationByManagerAntDate(
            Long managerId, LocalDate reservationDate
    ) {
        return queryFactory.select(reservation.reservationTime)
                .from(reservation)
                .join(reservation.managerAccount, managerAccount)
                .where(
                        managerAccount.id.eq(managerId),
                        reservation.reservationDate.eq(reservationDate)
                )
                .fetch();
    }

}

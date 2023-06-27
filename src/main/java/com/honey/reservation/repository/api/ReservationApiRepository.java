package com.honey.reservation.repository.api;

import com.honey.reservation.domain.reservation.Reservation;
import com.honey.reservation.dto.api.ReservationDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.honey.reservation.domain.QManagerAccount.managerAccount;
import static com.honey.reservation.domain.QUserAccount.userAccount;
import static com.honey.reservation.domain.reservation.QReservation.reservation;
import static com.querydsl.core.types.Order.DESC;

@Repository
public class ReservationApiRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ReservationApiRepository(EntityManager em) {
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

        /*
        long total = queryFactory
                .select(Projections.constructor(ReservationDto.class
                        , reservation.id, managerAccount.id, userAccount.loginId, reservation.reservationDate, reservation.reservationTime, reservation.reservationStatus, reservation.memo
                ))
                .from(reservation)
                .join(reservation.managerAccount, managerAccount)
                .join(reservation.userAccount, userAccount)
                .fetchCount();
         */

        long totalElements = queryFactory
                .select(reservation.count())
                .from(reservation)
                .fetchOne();

        return new PageImpl<>(contents, pageable, totalElements);
    }

}

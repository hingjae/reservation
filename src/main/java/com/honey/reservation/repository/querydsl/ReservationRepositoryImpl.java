package com.honey.reservation.repository.querydsl;

import com.honey.reservation.domain.QManagerAccount;
import com.honey.reservation.domain.reservation.QReservation;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ReservationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LocalTime> findReservationTime(LocalDate reservationDate, Long managerId) {
        QReservation reservation = QReservation.reservation;
        QManagerAccount managerAccount = QManagerAccount.managerAccount;
        return queryFactory
                .select(reservation.reservationTime)
                .from(reservation)
                .join(reservation.managerAccount, managerAccount)
                .where(
                        reservation.reservationDate.eq(reservationDate),
                        reservation.managerAccount.id.eq(managerId)
                )
                .fetch();
    }

    @Override
    public Long findReservation(Long managerId, LocalDate reservationDate, LocalTime reservationTime) {
        QReservation reservation = QReservation.reservation;
        return queryFactory
                .select(reservation.id)
                .from(reservation)
                .where(
                        reservation.managerAccount.id.eq(managerId),
                        reservation.reservationDate.eq(reservationDate),
                        reservation.reservationTime.eq(reservationTime)
                )
                .fetchFirst();
    }


}

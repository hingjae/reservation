package com.honey.reservation.domain.reservation;

import com.honey.reservation.domain.ManagerAccount;
import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.baseentity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@ToString(callSuper = true)
@Getter
@Table(indexes = {
        @Index(columnList = "reservationDate"),
        @Index(columnList = "reservationTime"),
        @Index(columnList = "reservationStatus"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@Entity
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @Setter
    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "manager_account_id")
    private ManagerAccount managerAccount;

    @Setter private LocalDate reservationDate;
    @Setter private LocalTime reservationTime;
    @Setter @Column(length = 1000) private String memo;
    @Setter @Enumerated(EnumType.STRING) private ReservationStatus reservationStatus;

    protected Reservation() {}

    private Reservation(Long id, UserAccount userAccount, ManagerAccount managerAccount, LocalDate reservationDate, LocalTime reservationTime, String memo, ReservationStatus reservationStatus) {
        this.id = id;
        this.userAccount = userAccount;
        this.managerAccount = managerAccount;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.memo = memo;
        this.reservationStatus = reservationStatus;
    }

    public static Reservation of(Long id, UserAccount userAccount, ManagerAccount managerAccount, LocalDate reservationDate, LocalTime reservationTime, String memo, ReservationStatus reservationStatus) {
        return new Reservation(id, userAccount, managerAccount, reservationDate, reservationTime, memo, reservationStatus);
    }

    public static Reservation of(UserAccount userAccount, ManagerAccount managerAccount, LocalDate reservationDate, LocalTime reservationTime, String memo, ReservationStatus reservationStatus) {
        return Reservation.of(null, userAccount, managerAccount, reservationDate, reservationTime, memo, reservationStatus);
    }

    public static Boolean isStatusReady(Reservation reservation) {
        return reservation.getReservationStatus().equals(ReservationStatus.READY);
    }
}

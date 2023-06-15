package com.honey.reservation.domain.reservation;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.baseentity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.FetchType.LAZY;

@ToString(callSuper = true)
@Getter
@Table(indexes = {
        @Index(columnList = "localDate"),
        @Index(columnList = "localTime"),
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

    @Setter private LocalDate localDate;
    @Setter private LocalTime localTime;
    @Setter @Column(length = 1000) private String memo;
    @Setter @Enumerated(EnumType.STRING) private ReservationStatus reservationStatus;

    protected Reservation() {}

    private Reservation(Long id, UserAccount userAccount, LocalDate localDate, LocalTime localTime, String memo, ReservationStatus reservationStatus) {
        this.id = id;
        this.userAccount = userAccount;
        this.localDate = localDate;
        this.localTime = localTime;
        this.memo = memo;
        this.reservationStatus = reservationStatus;
    }

    public static Reservation of(Long id, UserAccount userAccount, LocalDate localDate, LocalTime localTime, String memo, ReservationStatus reservationStatus) {
        return new Reservation(id, userAccount, localDate, localTime, memo, reservationStatus);
    }

    public static Reservation of(UserAccount userAccount, LocalDate localDate, LocalTime localTime, String memo, ReservationStatus reservationStatus) {
        return Reservation.of(null, userAccount, localDate, localTime, memo, reservationStatus);
    }
}

package com.honey.reservation.domain.reservation;

import com.honey.reservation.domain.UserAccount;
import com.honey.reservation.domain.baseentity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@ToString(callSuper = true)
@Getter
@Table(indexes = {
        @Index(columnList = "year"),
        @Index(columnList = "month"),
        @Index(columnList = "time"),
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

    @Setter @Column(nullable = false) private Integer year;
    @Setter @Column(nullable = false) private Integer month;
    @Setter @Column(nullable = false) private Integer day;
    @Setter @Column(nullable = false) private Double time;

    @Setter @Column(length = 1000) private String memo;
    @Setter @Enumerated(EnumType.STRING) private ReservationStatus reservationStatus;

    protected Reservation() {}

    private Reservation(Long id, UserAccount userAccount, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus) {
        this.id = id;
        this.userAccount = userAccount;
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
        this.memo = memo;
        this.reservationStatus = reservationStatus;
    }

    public static Reservation of(Long id, UserAccount userAccount, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus) {
        return new Reservation(id, userAccount, year, month, day, time, memo, reservationStatus);
    }

    public static Reservation of(UserAccount userAccount, Integer year, Integer month, Integer day, Double time, String memo, ReservationStatus reservationStatus) {
        return Reservation.of(null, userAccount, year, month, day, time, memo, reservationStatus);
    }
}

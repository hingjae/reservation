package com.honey.reservation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAccount is a Querydsl query type for UserAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAccount extends EntityPathBase<UserAccount> {

    private static final long serialVersionUID = 1936691290L;

    public static final QUserAccount userAccount = new QUserAccount("userAccount");

    public final com.honey.reservation.domain.baseentity.QBaseTimeEntity _super = new com.honey.reservation.domain.baseentity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath loginId = createString("loginId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<com.honey.reservation.domain.reservation.Reservation, com.honey.reservation.domain.reservation.QReservation> reservations = this.<com.honey.reservation.domain.reservation.Reservation, com.honey.reservation.domain.reservation.QReservation>createList("reservations", com.honey.reservation.domain.reservation.Reservation.class, com.honey.reservation.domain.reservation.QReservation.class, PathInits.DIRECT2);

    public QUserAccount(String variable) {
        super(UserAccount.class, forVariable(variable));
    }

    public QUserAccount(Path<? extends UserAccount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserAccount(PathMetadata metadata) {
        super(UserAccount.class, metadata);
    }

}


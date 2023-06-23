package com.honey.reservation.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QManagerAccount is a Querydsl query type for ManagerAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QManagerAccount extends EntityPathBase<ManagerAccount> {

    private static final long serialVersionUID = -760648120L;

    public static final QManagerAccount managerAccount = new QManagerAccount("managerAccount");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QManagerAccount(String variable) {
        super(ManagerAccount.class, forVariable(variable));
    }

    public QManagerAccount(Path<? extends ManagerAccount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QManagerAccount(PathMetadata metadata) {
        super(ManagerAccount.class, metadata);
    }

}


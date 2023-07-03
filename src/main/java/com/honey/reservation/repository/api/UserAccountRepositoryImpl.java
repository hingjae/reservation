package com.honey.reservation.repository.api;

import com.honey.reservation.dto.api.UserAccountDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.honey.reservation.domain.QUserAccount.userAccount;

public class UserAccountRepositoryImpl implements UserAccountRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public UserAccountRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<UserAccountDto> searchBySearchValue(Pageable pageable, String searchValue) {
        List<UserAccountDto> contents = queryFactory
                .select(Projections.constructor(
                        UserAccountDto.class,
                        userAccount.loginId, userAccount.name, userAccount.phoneNumber
                ))
                .from(userAccount)
                .where(usernameContains(searchValue))
                .fetch();

        Long total = queryFactory
                .select(userAccount.count())
                .from(userAccount)
                .where(usernameContains(searchValue))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private BooleanExpression usernameContains(String searchValue) {
        return searchValue != null ? userAccount.name.contains(searchValue) : null;
    }
}

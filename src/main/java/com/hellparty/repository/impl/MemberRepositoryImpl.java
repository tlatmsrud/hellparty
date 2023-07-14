package com.hellparty.repository.impl;

import com.hellparty.repository.custom.MemberRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.hellparty.domain.QMemberEntity.memberEntity;

/**
 * title        : MemberRepositoryImpl
 * author       : sim
 * date         : 2023-07-14
 * description  : MemberRepositoryCustom 구현체 클래스
 */

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Long findMemberIdByEmail(String email) {
        return queryFactory.select(memberEntity.id)
                .from(memberEntity)
                .where(
                        eqEmail(email)
                ).fetchOne();
    }

    public BooleanExpression eqEmail(String email){
        return email == null ? null : memberEntity.email.eq(email);
    }
}

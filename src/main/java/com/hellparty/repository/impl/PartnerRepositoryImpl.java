package com.hellparty.repository.impl;

import com.hellparty.dto.PartnerDTO;
import com.hellparty.repository.custom.PartnerRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hellparty.domain.QPartnerEntity.partnerEntity;

/**
 * title        : 파트너 리포지토리 구현체
 * author       : sim
 * date         : 2023-07-09
 * description  : QueryDsl을 적용한 파트너 리포지토리 구현체 클래스
 */

@RequiredArgsConstructor
public class PartnerRepositoryImpl implements PartnerRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<PartnerDTO> getPartnerList(Long memberId) {

        return queryFactory
                .select(Projections.constructor(
                        PartnerDTO.class
                        ,partnerEntity.id
                        ,partnerEntity.partner.nickname
                        ,partnerEntity.partner.execStatus))
                .from(partnerEntity)
                .where(
                        eqMemberId(memberId)
                ).fetch();
    }

    public BooleanExpression eqMemberId(Long memberId){
        return memberId == null ? null : partnerEntity.member.id.eq(memberId);
    }
}

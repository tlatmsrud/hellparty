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

    /**
     * 파트너 리스트 조회
     * @param memberId - 사용자 ID
     * @return 사용자의 파트너 리스트
     */
    @Override
    public List<PartnerDTO> getPartnerList(Long memberId) {

        return queryFactory
                .select(Projections.constructor(
                        PartnerDTO.class
                        ,partnerEntity.id
                        ,partnerEntity.partner.id
                        ,partnerEntity.partner.nickname
                        ,partnerEntity.partner.profileUrl
                        ,partnerEntity.partner.execStatus))
                .from(partnerEntity)
                .where(
                        eqMemberId(memberId)
                ).fetch();
    }

    /**
     * 파트너쉽 삭제
     * @param loginId - 로그인 ID
     * @param partnerId - 파트너 ID
     */
    @Override
    public void deletePartnership(Long loginId, Long partnerId) {
        queryFactory.delete(partnerEntity)
                .where(
                        eqMemberId(loginId).and(eqPartnerId(partnerId))
                                .or(eqMemberId(partnerId).and(eqPartnerId(loginId)))
                ).execute();
    }

    public BooleanExpression eqMemberId(Long memberId){
        return memberId == null ? null : partnerEntity.member.id.eq(memberId);
    }
    public BooleanExpression eqPartnerId(Long partnerId){
        return partnerId == null ? null : partnerEntity.partner.id.eq(partnerId);
    }

}

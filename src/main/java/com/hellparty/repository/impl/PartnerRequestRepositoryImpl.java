package com.hellparty.repository.impl;

import com.hellparty.domain.QMemberEntity;
import com.hellparty.dto.PartnerRequestDTO;
import com.hellparty.repository.custom.PartnerRequestRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.hellparty.domain.QPartnerRequestEntity.partnerRequestEntity;

/**
 * title        : 파트너 요청 리포지토리 구현체
 * author       : sim
 * date         : 2023-07-07
 * description  : 파트너 요청 리포지토리 구현체 클래스
 */

@RequiredArgsConstructor
public class PartnerRequestRepositoryImpl implements PartnerRequestRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * 파트너 요청한 리스트 조회
     * @param memberId - 사용자 ID
     * @param pageable - 페이지 객체
     * @return 파트너 요청 리스트 조회
     */
    @Override
    public Page<PartnerRequestDTO.History> findPartnerRequestList(Long memberId, Pageable pageable) {
        List<PartnerRequestDTO.History> list = queryFactory
                .select(Projections.constructor(PartnerRequestDTO.History.class
                        ,partnerRequestEntity.id
                        ,partnerRequestEntity.responseStatus
                        ,partnerRequestEntity.toMember.id
                        ,partnerRequestEntity.toMember.nickname
                        ,partnerRequestEntity.toMember.profileUrl
                ))
                .from(partnerRequestEntity)
                .where(
                        eqMemberIdWithMemberEntity(partnerRequestEntity.fromMember, memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(partnerRequestEntity.count())
                .from(partnerRequestEntity)
                .where(
                        eqMemberIdWithMemberEntity(partnerRequestEntity.fromMember, memberId))
                .fetchOne();

        return new PageImpl<>(list, pageable, totalCount);
    }

    /**
     * 파트너 요청 받은 리스트 조회
     * @param memberId - 사용자 ID
     * @param pageable - 페이지 객체
     * @return 파트너 요청 받은 리스트 조회
     */
    @Override
    public Page<PartnerRequestDTO.History> findPartnerRequestToMeList(Long memberId, Pageable pageable) {
        List<PartnerRequestDTO.History> list = queryFactory
                .select(Projections.constructor(PartnerRequestDTO.History.class
                        ,partnerRequestEntity.id
                        ,partnerRequestEntity.responseStatus
                        ,partnerRequestEntity.fromMember.id
                        ,partnerRequestEntity.fromMember.nickname
                        ,partnerRequestEntity.fromMember.profileUrl
                        ))
                .from(partnerRequestEntity)
                .where(
                    eqMemberIdWithMemberEntity(partnerRequestEntity.toMember, memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(partnerRequestEntity.count())
                .from(partnerRequestEntity)
                .where(
                        eqMemberIdWithMemberEntity(partnerRequestEntity.toMember, memberId))
                .fetchOne();

        return new PageImpl<>(list, pageable, totalCount);
    }

    public BooleanExpression eqMemberIdWithMemberEntity(QMemberEntity memberEntity, Long memberId){
        return memberId == null ? null : memberEntity.id.eq(memberId);
    }
}

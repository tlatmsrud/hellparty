package com.hellparty.repository.impl;

import com.hellparty.domain.PartnerRequestEntity;
import com.hellparty.domain.QMemberEntity;
import com.hellparty.domain.QPartnerRequestEntity;
import com.hellparty.dto.PartnerRequestDTO;
import com.hellparty.mapper.PartnerRequestMapper;
import com.hellparty.repository.custom.PartnerRequestRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

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
    private final PartnerRequestMapper partnerRequestMapper;

    /**
     * 파트너 요청 리스트 조회
     * @param memberId - 사용자 ID
     * @param pageable - 페이지 객체
     * @return 사용자가 요청한 파트너 요청 리스트 조회
     */
    @Override
    public Page<PartnerRequestDTO> findPartnerRequestList(Long memberId, Pageable pageable) {
        List<PartnerRequestEntity> list = queryFactory
                .selectFrom(partnerRequestEntity)
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

        List<PartnerRequestDTO> result = list.stream()
                .map(partnerRequestMapper::entityToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(result, pageable, totalCount);
    }

    /**
     * 타인이 요청한 파트너 요청 리스트 조회
     * @param memberId - 사용자 ID
     * @param pageable - 페이지 객체
     * @return 타인이 요청한 파트너 요청 리스트 조회
     */
    @Override
    public Page<PartnerRequestDTO> findPartnerRequestToMeList(Long memberId, Pageable pageable) {
        List<PartnerRequestEntity> list = queryFactory
                .selectFrom(partnerRequestEntity)
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

        List<PartnerRequestDTO> result = list.stream()
                .map(partnerRequestMapper::entityToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(result, pageable, totalCount);
    }

    public BooleanExpression eqMemberIdWithMemberEntity(QMemberEntity memberEntity, Long memberId){
        return memberId == null ? null : memberEntity.id.eq(memberId);
    }
}

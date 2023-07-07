package com.hellparty.repository.impl;

import com.hellparty.domain.PartnerRequest;
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

import static com.hellparty.domain.QPartnerRequest.partnerRequest;

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
    @Override
    public Page<PartnerRequestDTO> findPartnerRequestList(Long memberId, Pageable pageable) {
        List<PartnerRequest> list = queryFactory
                .selectFrom(partnerRequest)
                .where(
                        eqMemberId(memberId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(partnerRequest.count())
                .from(partnerRequest)
                .where(eqMemberId(memberId))
                .fetchOne();

        List<PartnerRequestDTO> result = list.stream()
                .map(partnerRequestMapper::entityToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(result, pageable, totalCount);
    }

    public BooleanExpression eqMemberId(Long memberId){
        return memberId == null ? null : partnerRequest.fromMember.id.eq(memberId);
    }
}

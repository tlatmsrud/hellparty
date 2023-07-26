package com.hellparty.repository.impl;

import com.hellparty.repository.custom.ChattingRoomRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.hellparty.domain.QChattingRoomEntity.chattingRoomEntity;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-20
 * description  :
 */

@RequiredArgsConstructor
public class ChattingRoomRepositoryImpl implements ChattingRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 채팅방 ID 조회
     * @param fromMemberId - 자신의 사용자 ID
     * @param toMemberId - 상대방 사용자 ID
     * @return 채팅방 ID
     */
    @Override
    public Long findIdByMemberId(Long fromMemberId, Long toMemberId) {
        return queryFactory.select(chattingRoomEntity.id)
                .from(chattingRoomEntity)
                .where(
                        eqMemberId(fromMemberId, toMemberId)
                ).fetchOne();
    }

    public BooleanExpression eqMemberId(Long fromMemberId, Long toMemberId){
        return fromMemberId == null || toMemberId == null ? null :
                chattingRoomEntity.fromMemberId.eq(fromMemberId).and(chattingRoomEntity.toMemberId.eq(toMemberId))
                        .or(chattingRoomEntity.fromMemberId.eq(toMemberId).and(chattingRoomEntity.toMemberId.eq(fromMemberId)));
    }
}

package com.hellparty.repository.impl;

import com.hellparty.dto.ChatDTO;
import com.hellparty.dto.ChattingHistoryDTO;
import com.hellparty.repository.custom.ChattingRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;


import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.hellparty.domain.QChattingEntity.chattingEntity;


/**
 * title        : 채팅 커스텀 리포지토리 구현체 클래스
 * author       : sim
 * date         : 2023-07-20
 * description  :
 */

@RequiredArgsConstructor
public class ChattingRepositoryImpl implements ChattingRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final JdbcTemplate jdbcTemplate;

    /**
     * 채팅 내역 조회
     * @param roomId - 채팅방 ID
     * @return 채팅 내역 조회
     */
    @Override
    public List<ChattingHistoryDTO> findByChattingRoomId(Long roomId) {
        return queryFactory.select(Projections.constructor(
                ChattingHistoryDTO.class
                ,chattingEntity.fromMemberId
                ,chattingEntity.sendMessage
                ,chattingEntity.sendTime))
                .from(chattingEntity)
                .where(eqRoomId(roomId))
                .fetch();
    }

    /**
     * 채팅 내역 저장
     * @param list - 채팅내역 리스트
     */
    @Override
    public void saveAllChatting(List<ChatDTO> list) {

        jdbcTemplate.batchUpdate(
                "INSERT INTO TBL_CHATTING(ID, CHATTING_ROOM_ID, FROM_MEMBER_ID, SEND_MESSAGE, SEND_DATE, CREATE_DATE)"+
                        "VALUES(NEXTVAL('TBL_CHATTING_SEQ'),?, ?, ?, ?, ?)"
                ,list
                ,10
                ,(PreparedStatement ps, ChatDTO chatDto) -> {
                    ps.setLong(1, chatDto.getRoomId());
                    ps.setLong(2, chatDto.getWriterId());
                    ps.setString(3, chatDto.getMessage());
                    ps.setTimestamp(4, Timestamp.valueOf(chatDto.getTime()));
                    ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                });

    }


    public BooleanExpression eqRoomId(Long roomId){
        return roomId == null ? null : chattingEntity.chattingRoom.id.eq(roomId);
    }
}

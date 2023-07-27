package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.domain.ChattingRoomEntity;
import com.hellparty.repository.ChattingRepository;
import com.hellparty.repository.ChattingRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-26
 * description  :
 */
class ChattingServiceTest implements TestFixture {

    private ChattingRoomRepository chattingRoomRepository = mock(ChattingRoomRepository.class);
    private RedisService redisService = mock(RedisService.class);
    private ChattingRepository chattingRepository = mock(ChattingRepository.class);
    private final ChattingService chattingService = new ChattingService(chattingRoomRepository, chattingRepository, redisService);

    @BeforeEach
    void setUp(){
        given(chattingRoomRepository.findIdByMemberId(LOGIN_MEMBER_ID, VALID_MEMBER_ID))
                .willReturn(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER);

        given(chattingRoomRepository.findIdByMemberId(LOGIN_MEMBER_ID, NEVER_CHATTING_MEMBER_ID))
                .willReturn(null);

        given(chattingRoomRepository.save(any(ChattingRoomEntity.class))).will(invocation -> {
                    ChattingRoomEntity source = invocation.getArgument(0);
                    return ChattingRoomEntity.builder()
                            .id(CREATED_CHATTING_ROOM_ID)
                            .fromMemberId(source.getFromMemberId())
                            .toMemberId(source.getToMemberId())
                            .build();
                });

        given(redisService.getChattingHistory(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER.toString())).willReturn(
                CHATTING_HISTORY_DTO_LIST
        );
    }
    @Test
    @DisplayName("채팅방이 있는 유저에 대한 채팅룸 조회")
    void getChattingRoomWhenExistChattingRoom() {
        assertThat(chattingService.getChattingRoom(LOGIN_MEMBER_ID, VALID_MEMBER_ID))
                .isEqualTo(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER);

        verify(chattingRoomRepository).findIdByMemberId(eq(LOGIN_MEMBER_ID), eq(VALID_MEMBER_ID));
        verify(chattingRoomRepository, never()).save(any(ChattingRoomEntity.class));

    }

    @Test
    @DisplayName("채팅방이 없는 유저에 대한 채팅룸 조회 - 채팅룸 생성 > 조회")
    void getChattingRoomWhenNotExistChattingRoom() {
        assertThat(chattingService.getChattingRoom(LOGIN_MEMBER_ID, NEVER_CHATTING_MEMBER_ID))
                .isEqualTo(CREATED_CHATTING_ROOM_ID);

        verify(chattingRoomRepository).findIdByMemberId(eq(LOGIN_MEMBER_ID), eq(NEVER_CHATTING_MEMBER_ID));
        verify(chattingRoomRepository).save(any(ChattingRoomEntity.class));

    }

    @Test
    @DisplayName("채팅방 ID에 대한 채팅내역 조회")
    void getChattingHistory() {
        assertThat(chattingService.getChattingHistory(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER))
                .isEqualTo(CHATTING_HISTORY_DTO_LIST);
    }

}
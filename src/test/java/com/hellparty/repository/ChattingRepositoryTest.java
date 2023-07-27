package com.hellparty.repository;

import attributes.TestConfig;
import attributes.TestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-27
 * description  :
 */

@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource(properties = { "spring.config.location=classpath:application-db.yml" })
public class ChattingRepositoryTest implements TestFixture {

    @Autowired
    private ChattingRepository chattingRepository;

    @Test
    @DisplayName("채팅방 ID에 대한 채팅 내역 조회")
    public void findByChattingRoomIdWithValidRoomId(){
        assertThat(chattingRepository.findByChattingRoomId(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER))
                .hasSize(6);
    }

    @Test
    @DisplayName("채팅방 ID에 대한 채팅 내역 조회")
    public void findByChattingRoomIdWithInvalidRoomId(){
        assertThat(chattingRepository.findByChattingRoomId(INVALID_CHATTING_ROOM_ID))
                .hasSize(0);
    }


    @Test
    @DisplayName("채팅내역 추가")
    public void saveAllChatting(){
        chattingRepository.saveAllChatting(ADD_CHAT_DTO_LIST);
        assertThat(chattingRepository.findByChattingRoomId(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER))
                .hasSize(8);
    }
}

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
 * title        : ChattingRoomRepository 테스트
 * author       : sim
 * date         : 2023-07-27
 * description  : ChattingRoomRepository 테스트 클래스
 */

@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource(properties = { "spring.config.location=classpath:application-db.yml" })
public class ChattingRoomRepositoryTest implements TestFixture {

    @Autowired
    private ChattingRoomRepository chattingRoomRepository;

    @Test
    @DisplayName("채팅방이 존재하는 사용자에 대한 채팅방 ID 조회")
    public void findIdByMemberIdWithExistChattingRoom(){
        assertThat(chattingRoomRepository.findIdByMemberId(LOGIN_MEMBER_ID, PARTNER_ID_OF_LOGIN_MEMBER))
                .isEqualTo(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER);
    }

    @Test
    @DisplayName("채팅방이 존재하지 않는 사용자에 대한 채팅방 ID 조회")
    public void findIdByMemberIdWithNoExistChattingRoom(){
        assertThat(chattingRoomRepository.findIdByMemberId(LOGIN_MEMBER_ID, VALID_MEMBER_ID))
                .isEqualTo(null);
    }
}

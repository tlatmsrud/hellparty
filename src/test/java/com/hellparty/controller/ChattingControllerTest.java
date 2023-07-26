package com.hellparty.controller;

import attributes.TestFixture;
import attributes.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hellparty.service.ChattingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-26
 * description  :
 */

@WebMvcTest(ChattingController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(HttpEncodingAutoConfiguration.class)

class ChattingControllerTest implements TestFixture {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChattingService chattingService;

    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp(){
        setObjectMapper();
        given(chattingService.getChattingRoom(LOGIN_MEMBER_ID, VALID_MEMBER_ID))
                .willReturn(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER);

        given(chattingService.getChattingHistory(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER))
                .willReturn(CHATTING_HISTORY_DTO);
    }

    void setObjectMapper(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
    @Test
    @TestMemberAuth
    @DisplayName("상대 유저에 대한 채팅방 ID 조회")
    void getChattingRoom() throws Exception {
        mockMvc.perform(
                get("/api/chatting/room/{memberId}",VALID_MEMBER_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER.toString()));
    }

    @Test
    @TestMemberAuth
    @DisplayName("채팅방 ID에 대한 채팅내역 조회")
    void getChatHistory() throws Exception {
        mockMvc.perform(
                        get("/api/chatting/history/{roomId}",CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(CHATTING_HISTORY_DTO)));
    }
}
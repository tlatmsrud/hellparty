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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * title        : ChattingControllerTest
 * author       : sim
 * date         : 2023-07-26
 * description  : ChattingControllerTest
 */

@WebMvcTest(ChattingController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(HttpEncodingAutoConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)

class ChattingControllerTest implements TestFixture {

    private MockMvc mockMvc;

    @MockBean
    private ChattingService chattingService;

    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation){
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .addFilter(encodingFilter)
                .build();

        setObjectMapper();
        given(chattingService.getChattingRoom(LOGIN_MEMBER_ID, VALID_MEMBER_ID))
                .willReturn(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER);

        given(chattingService.getChattingHistory(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER))
                .willReturn(CHATTING_HISTORY_DTO_LIST);
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
                RestDocumentationRequestBuilders.get("/api/chatting/room/{memberId}",VALID_MEMBER_ID)
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER.toString()))
                .andDo(print())
                .andDo(document(
                        "getChattingRoom"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , pathParameters(
                                parameterWithName("memberId").description("상대방 사용자 ID"))
                        , responseBody())
                );
    }

    @Test
    @TestMemberAuth
    @DisplayName("채팅방 ID에 대한 채팅내역 조회")
    void getChatHistory() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/chatting/history/{roomId}",CHATTING_ROOM_ID_FOR_LOGIN_MEMBER_AND_VALID_MEMBER)
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(CHATTING_HISTORY_DTO_LIST)))
                .andDo(print())
                .andDo(document(
                        "getChatHistory"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , pathParameters(
                                parameterWithName("roomId").description("채팅방 ID"))
                        , responseFields(
                                fieldWithPath("[]").description("채팅내역 리스트")
                                ,fieldWithPath("[].memberId").description("사용자 ID")
                                ,fieldWithPath("[].message").description("메시지")
                                ,fieldWithPath("[].time").description("시간"))
                        )
                );
    }
}
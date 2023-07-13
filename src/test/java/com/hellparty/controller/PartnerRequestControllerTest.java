package com.hellparty.controller;

import attributes.TestFixture;
import attributes.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.dto.PartnerRequestDTO;
import com.hellparty.exception.BadRequestException;
import com.hellparty.exception.NotFoundException;
import com.hellparty.service.PartnerRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-07
 * description  :
 */

@WebMvcTest(PartnerRequestController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(HttpEncodingAutoConfiguration.class)
class PartnerRequestControllerTest implements TestFixture {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private PartnerRequestService partnerRequestService;

    @BeforeEach
    void setUp(){
        willDoNothing().given(partnerRequestService)
                .requestPartner(eq(LOGIN_MEMBER_ID), eq(VALID_MEMBER_ID));

        willThrow(new NotFoundException("대상 사용자를 찾을 수 없습니다. 다시 시도해주세요.")).given(partnerRequestService)
                .requestPartner(eq(LOGIN_MEMBER_ID), eq(INVALID_MEMBER_ID));

        willThrow(new BadRequestException("대상 사용자는 파트너를 구하지 않는 상태입니다. 다른 사용자를 찾아보세요.")).given(partnerRequestService)
                .requestPartner(eq(LOGIN_MEMBER_ID), eq(NOT_LOOKING_FOR_PARTNER_MEMBER_ID));

        given(partnerRequestService.getPartnerRequestList(eq(LOGIN_MEMBER_ID), any(Pageable.class)))
                .willReturn(new PageImpl<>(PARTNER_REQUEST_HISTORY_FROM_LOGIN_MEMBER,DEFAULT_PAGEABLE,PARTNER_REQUEST_HISTORY_FROM_LOGIN_MEMBER.size()));

        given(partnerRequestService.getPartnerRequestToMeList(eq(LOGIN_MEMBER_ID), any(Pageable.class)))
                .willReturn(new PageImpl<>(PARTNER_REQUEST_HISTORY_TO_LOGIN_MEMBER,DEFAULT_PAGEABLE,PARTNER_REQUEST_HISTORY_TO_LOGIN_MEMBER.size()));

        willDoNothing().given(partnerRequestService)
                .answerPartnerRequest(eq(LOGIN_MEMBER_ID), any(PartnerRequestDTO.Answer.class));

        willThrow(new BadRequestException("잘못된 요청입니다. 다시 시도해주세요.")).given(partnerRequestService)
                .cancelPartner(LOGIN_MEMBER_ID, PARTNER_REQUEST_ID_TO_LOGIN_MEMBER);

    }
    @Test
    @TestMemberAuth
    void requestPartnerWithValidMemberId() throws Exception {
        mockMvc.perform(
                post("/api/partner-req/{toMemberId}", VALID_MEMBER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    @TestMemberAuth
    void requestPartnerWithInvalidMemberId() throws Exception {
        mockMvc.perform(
                post("/api/partner-req/{toMemberId}", INVALID_MEMBER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isNotFound())
                .andExpect(content().string(containsString("대상 사용자를 찾을 수 없습니다. 다시 시도해주세요.")));
    }

    @Test
    @TestMemberAuth
    void requestPartnerWithMemberIdForNotLookingForPartner() throws Exception {
        mockMvc.perform(
                        post("/api/partner-req/{toMemberId}", NOT_LOOKING_FOR_PARTNER_MEMBER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                ).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("대상 사용자는 파트너를 구하지 않는 상태입니다. 다른 사용자를 찾아보세요.")));
    }

    @Test
    @TestMemberAuth
    void getPartnerRequestList() throws Exception{
        mockMvc.perform(
                get("/api/partner-req")
        ).andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(PARTNER_REQUEST_HISTORY_FROM_LOGIN_MEMBER))))
                .andExpect(content().string(containsString("pageable")));
    }

    @Test
    @TestMemberAuth
    void getPartnerRequestToMeList() throws Exception{
        mockMvc.perform(
                get("/api/partner-req/to-me")
        ).andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(PARTNER_REQUEST_HISTORY_TO_LOGIN_MEMBER))))
                .andExpect(content().string(containsString("pageable")));
    }


    @Test
    @TestMemberAuth
    void answerPartnerRequest() throws Exception{
        mockMvc.perform(
                post("/api/partner-req/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_PARTNER_REQUEST_ANSWER))
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    @TestMemberAuth
    @DisplayName("로그인한 사용자가 했던 파트너 요청 취소")
    void cancelPartner() throws Exception{
        mockMvc.perform(
                delete("/api/partner-req/{requestId}", PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER)
                        .with(csrf())
        ).andExpect(status().isNoContent());

        verify(partnerRequestService).cancelPartner(LOGIN_MEMBER_ID, PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER);
    }

    @Test
    @TestMemberAuth
    @DisplayName("다른 사용자의 파트너 요청 취소 - BadRequestException")
    void cancelPartnerWithNotMatchingLoginId() throws Exception{
        mockMvc.perform(
                delete("/api/partner-req/{requestId}", PARTNER_REQUEST_ID_TO_LOGIN_MEMBER)
                        .with(csrf())
        ).andExpect(status().isBadRequest());

        verify(partnerRequestService).cancelPartner(LOGIN_MEMBER_ID, PARTNER_REQUEST_ID_TO_LOGIN_MEMBER);
    }


}
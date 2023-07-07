package com.hellparty.controller;

import attributes.TestFixture;
import attributes.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.exception.BadRequestException;
import com.hellparty.exception.NotFoundException;
import com.hellparty.service.PartnerRequestService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .willReturn(new PageImpl<>(PARTNER_REQUEST_DTO_LIST,DEFAULT_PAGEABLE,PARTNER_REQUEST_DTO_LIST.size()));
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
                .andExpect(content().string(containsString("20")));
    }

    @Test
    @TestMemberAuth
    void answerPartnerRequest() throws Exception{
        mockMvc.perform(
                post("/api/partner-req/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PARTNER_REQUEST_ANSWER))
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }
}
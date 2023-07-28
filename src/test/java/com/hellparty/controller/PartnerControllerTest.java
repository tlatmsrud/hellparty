package com.hellparty.controller;

import attributes.TestFixture;
import attributes.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.service.PartnerService;
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
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * title        : 파트너 컨트롤러 테스트
 * author       : sim
 * date         : 2023-07-06
 * description  : 파트너 컨트롤러 테스트 클래스
 */

@WebMvcTest(PartnerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(HttpEncodingAutoConfiguration.class)
class PartnerControllerTest implements TestFixture {

    @MockBean
    private PartnerService partnerService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        given(partnerService.getPartnerList(LOGIN_MEMBER_ID))
                .willReturn(PARTNER_DTO_LIST_FOR_LOGIN_MEMBER);
    }
    @Test
    @TestMemberAuth
    @DisplayName("로그인 사용자의 파트너 리스트 조회")
    void getPartnerListWithValidMemberId() throws Exception {
        mockMvc.perform(
                get("/api/partner/list")
        ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(PARTNER_DTO_LIST_FOR_LOGIN_MEMBER)));
    }

    @Test
    @TestMemberAuth
    @DisplayName("파트너 삭제")
    void deletePartner() throws Exception{
        mockMvc.perform(
                delete("/api/partner/{partnerId}", VALID_MEMBER_ID)
                .with(csrf())
        ).andExpect(status().isNoContent());

        verify(partnerService).deletePartner(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
    }
}
package com.hellparty.controller;

import attributes.TestFixture;
import attributes.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.SearchMemberDTO;
import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * title        : Member Controller Mvc Test
 * author       : sim
 * date         : 2023-07-28
 * description  : 사용자 컨트롤러 테스트 클래스
 */


@WebMvcTest(MemberController.class) // Web layer만 동작하도록
@MockBean(JpaMetamodelMappingContext.class)
@Import(HttpEncodingAutoConfiguration.class)
class MemberControllerTest implements TestFixture {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){

        given(memberService.getDetail(LOGIN_MEMBER_ID))
                .willReturn(LOGIN_MEMBER_DTO);

        given(memberService.getHealthDetail(LOGIN_MEMBER_ID))
                .willReturn(LOGIN_MEMBER_HEALTH_DTO);

        willDoNothing().given(memberService)
                .updateDetail(eq(VALID_MEMBER_ID), any(MemberDTO.Update.class));

        given(memberService.searchMemberList(eq(LOGIN_MEMBER_ID), any(SearchMemberDTO.Request.class)))
                .willReturn(SEARCH_MEMBER_SUMMARY_DTO);

        given(memberService.searchMemberDetail(VALID_MEMBER_ID))
                .willReturn(SEARCH_MEMBER_DETAIL_DTO);
    }

    @Test
    @TestMemberAuth
    @DisplayName("사용자 정보 조회")
    void getDetail() throws Exception {
        mockMvc.perform(
                        get("/api/member")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(LOGIN_MEMBER_DTO)));
    }

    @Test
    @TestMemberAuth
    @DisplayName("사용자 헬스 정보 조회")
    void getHealthDetail() throws Exception{
        mockMvc.perform(
                        get("/api/member/health")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(LOGIN_MEMBER_HEALTH_DTO)));
    }

    @Test
    @TestMemberAuth
    @DisplayName("유효 요청값을 통한 사용자 정보 수정 요청")
    void updateDetailWithValidRequest() throws Exception {
        mockMvc.perform(
                put("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_MEMBER_DETAIL_REQUEST))
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    @TestMemberAuth
    @DisplayName("유효하지 않은 요청값을 통한 사용자 정보 수정 요청 - BadRequest 예외발생")
    void updateDetailWithInvalidRequest() throws Exception {
        mockMvc.perform(
                put("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_MEMBER_DETAIL_REQUEST_WITHOUT_NICKNAME))
                        .with(csrf())
        ).andExpect(status().isBadRequest());
    }


    @Test
    @TestMemberAuth
    @DisplayName("헬스정보 수정")
    void updateHealthDetail() throws Exception{
        mockMvc.perform(
                put("/api/member/health")
                        .content(objectMapper.writeValueAsString(UPDATE_HEALTH_DETAIL_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    @TestMemberAuth
    @DisplayName("운동 상태정보 수정")
    void updateExecStatusToW() throws Exception{
        mockMvc.perform(
                patch("/api/member/exec-status")
                        .param("status", ExecStatus.W.name())
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    @TestMemberAuth
    @DisplayName("파트너 구함 상태 수정")
    void updatePartnerFindStatusToW() throws Exception{
        mockMvc.perform(
                patch("/api/member/partner-find-status")
                        .param("status", PartnerFindStatus.Y.name())
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    @TestMemberAuth
    @DisplayName("사용자 검색 - 리스트")
    void searchMemberList() throws Exception{
        mockMvc.perform(
                get("/api/member/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SEARCH_MEMBER_REQUEST_DTO)))
                .andExpect(content().string(objectMapper.writeValueAsString(SEARCH_MEMBER_SUMMARY_DTO)));
    }

    @Test
    @TestMemberAuth
    @DisplayName("사용자 검색 - 상세")
    void searchMemberDetail() throws Exception{
        mockMvc.perform(
                        get("/api/member/search-detail/{memberId}", VALID_MEMBER_ID))
                .andExpect(content().string(objectMapper.writeValueAsString(SEARCH_MEMBER_DETAIL_DTO)));
    }
}
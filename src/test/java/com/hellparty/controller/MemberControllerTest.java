package com.hellparty.controller;

import attributes.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.domain.embedded.Address;
import com.hellparty.domain.embedded.BigThree;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.ExecDayDTO;
import com.hellparty.dto.MemberHealthDTO;
import com.hellparty.enums.Division;
import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.enums.Sex;
import com.hellparty.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * title        : Member Controller Mvc Test
 * author       : sim
 * date         : 2023-07-01
 * description  : 사용자 컨트롤러 테스트 클래스
 */


@WebMvcTest(MemberController.class) // Web layer만 동작하도록
@MockBean(JpaMetamodelMappingContext.class)
@Import(HttpEncodingAutoConfiguration.class)
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Long VALID_MEMBER_ID = 1L;

    private final MemberDTO.Update UPDATE_DETAIL_REQUEST = MemberDTO.Update.builder()
            .age(10).height(170).weight(50.1).sex(Sex.M)
            .email("test@naver.com").nickname("nickname").profileUrl("test.url")
            .build();

    private final MemberHealthDTO.Update UPDATE_HEALTH_DETAIL_REQUEST = MemberHealthDTO.Update.builder()
            .execStartTime(Time.valueOf("19:00:00"))
            .execEndTime(Time.valueOf("20:00:00"))
            .bigThree(BigThree.builder()
                    .squat(100)
                    .daedlift(110)
                    .benchPress(80)
                    .build())
            .execArea(123L)
            .healthMotto("뇌는 근육으로 이루어져있다.")
            .gymAddress(Address.builder()
                    .y(1L)
                    .x(2L)
                    .address("서울시 중랑구")
                    .placeName("중랑헬스장")
                    .build())
            .spclNote("특이사항")
            .div(Division.THREE)
            .execDay(new ExecDayDTO(false, true, true, true, true,true,false))
            .build();

    @BeforeEach
    void setUp(){

        willDoNothing().given(memberService)
                .updateDetail(eq(VALID_MEMBER_ID), any(MemberDTO.Update.class));

        given(memberService.getDetail(VALID_MEMBER_ID)).willReturn(
                MemberDTO.builder()
                        .id(VALID_MEMBER_ID)
                        .nickname("테스터")
                        .build()
        );

        given(memberService.getHealthDetail(VALID_MEMBER_ID)).willReturn(
                MemberHealthDTO.builder()
                        .id(VALID_MEMBER_ID)
                        .div(Division.THREE)
                        .execArea(123L)
                        .build()
        );
    }

    @Test
    @TestMemberAuth
    void updateWithValidRequest() throws Exception {
        mockMvc.perform(
                put("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_DETAIL_REQUEST))
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    @TestMemberAuth
    void getDetail() throws Exception {
        mockMvc.perform(
                get("/api/member")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("테스터")));
    }

    @Test
    @TestMemberAuth
    void getHealthDetail() throws Exception{
        mockMvc.perform(
                get("/api/member/health")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("THREE")));
    }

    @Test
    @TestMemberAuth
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
    void updateExecStatusToW() throws Exception{
        mockMvc.perform(
                patch("/api/member/exec-status")
                        .param("status", ExecStatus.W.name())
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

    @Test
    @TestMemberAuth
    void updatePartnerFindStatusToW() throws Exception{
        mockMvc.perform(
                patch("/api/member/partner-find-status")
                        .param("status", PartnerFindStatus.Y.name())
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }

}
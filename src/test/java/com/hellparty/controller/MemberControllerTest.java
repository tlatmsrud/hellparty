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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
@ExtendWith(RestDocumentationExtension.class)
class MemberControllerTest implements TestFixture {

    @MockBean
    private MemberService memberService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation){
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .addFilter(encodingFilter)
                .build();

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
                                .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(LOGIN_MEMBER_DTO)))
                .andDo(
                        document("getDetail"
                                , requestHeaders(
                                        headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                                , responseFields(
                                        fieldWithPath("id").description("사용자 ID")
                                        ,fieldWithPath("nickname").description("닉네임")
                                        ,fieldWithPath("email").description("이메일 주소")
                                        ,fieldWithPath("profileUrl").description("프로필 이미지 URL")
                                        ,fieldWithPath("bodyProfileUrl").description("바디프로필 이미지 URL")
                                        ,fieldWithPath("birthYear").description("출생 연도")
                                        ,fieldWithPath("height").description("키")
                                        ,fieldWithPath("weight").description("몸무게")
                                        ,fieldWithPath("mbti").description("MBTI")
                                        ,fieldWithPath("sex").description("성별")
                                )
                        ));
    }

    @Test
    @TestMemberAuth
    @DisplayName("사용자 헬스 정보 조회")
    void getHealthDetail() throws Exception{
        mockMvc.perform(
                        get("/api/member/health")
                                .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(LOGIN_MEMBER_HEALTH_DTO)))
                .andDo(document("getHealthDetail"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , responseFields(
                                fieldWithPath("execStartTime").description("헬스 시작시간")
                                ,fieldWithPath("execEndTime").description("헬스 종료시간")
                                ,fieldWithPath("div").description("분할방식")
                                ,fieldWithPath("execArea").description("헬스 지역")
                                ,fieldWithPath("gymAddress").description("헬스장 주소")
                                ,fieldWithPath("gymAddress.placeName").description("헬스장 이름")
                                ,fieldWithPath("gymAddress.address").description("주소")
                                ,fieldWithPath("gymAddress.x").description("x 좌표")
                                ,fieldWithPath("gymAddress.y").description("y 좌표")
                                ,fieldWithPath("spclNote").description("특이사항")
                                ,fieldWithPath("bigThree").description("3대 중량")
                                ,fieldWithPath("bigThree.benchPress").description("벤치프레스")
                                ,fieldWithPath("bigThree.squat").description("스쿼트")
                                ,fieldWithPath("bigThree.deadlift").description("데드리프트")
                                ,fieldWithPath("healthMotto").description("헬스 좌우명")
                        )
                ));
    }

    @Test
    @TestMemberAuth
    @DisplayName("유효 요청값을 통한 사용자 정보 수정 요청")
    void updateDetailWithValidRequest() throws Exception {
        mockMvc.perform(
                put("/api/member")
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_MEMBER_DETAIL_REQUEST))
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(document(
                        "updateDetail"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , requestFields(
                                fieldWithPath("nickname").description("닉네임")
                                , fieldWithPath("profileUrl").description("프로필 이미지 URL")
                                , fieldWithPath("bodyProfileUrl").description("바디프로필 이미지 URL")
                                , fieldWithPath("birthYear").description("출생 연도")
                                , fieldWithPath("height").description("키")
                                , fieldWithPath("weight").description("몸무게")
                                , fieldWithPath("mbti").description("MBTI")
                                , fieldWithPath("sex").description("성별")
                        )

                ));

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
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .content(objectMapper.writeValueAsString(UPDATE_HEALTH_DETAIL_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document(
                        "updateHealthDetail"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , requestFields(
                                fieldWithPath("execStartTime").description("헬스 시작시간")
                                , fieldWithPath("execEndTime").description("헬스 종료시간")
                                , fieldWithPath("div").description("분할 방식")
                                , fieldWithPath("execArea").description("헬스 지역")
                                , fieldWithPath("gymAddress").description("헬스 주소")
                                , fieldWithPath("gymAddress.placeName").description("헬스장 이름")
                                , fieldWithPath("gymAddress.address").description("헬스장 주소")
                                , fieldWithPath("gymAddress.x").description("x 좌표")
                                , fieldWithPath("gymAddress.y").description("y 좌표")
                                , fieldWithPath("spclNote").description("특이사항")
                                , fieldWithPath("bigThree").description("3대 중량")
                                , fieldWithPath("bigThree.benchPress").description("벤치프레스")
                                , fieldWithPath("bigThree.squat").description("스쿼트")
                                , fieldWithPath("bigThree.deadlift").description("데드리프트")
                                , fieldWithPath("healthMotto").description("헬스 좌우명")
                                , fieldWithPath("execDay").description("헬스 날짜")
                                , fieldWithPath("execDay.sun").description("일요일")
                                , fieldWithPath("execDay.mon").description("월요일")
                                , fieldWithPath("execDay.tue").description("화요일")
                                , fieldWithPath("execDay.wed").description("수요일")
                                , fieldWithPath("execDay.thu").description("목요일")
                                , fieldWithPath("execDay.fri").description("금요일")
                                , fieldWithPath("execDay.sat").description("토요일")
                        )

                ));


    }

    @Test
    @TestMemberAuth
    @DisplayName("운동 상태정보 수정")
    void updateExecStatusToW() throws Exception{
        mockMvc.perform(
                patch("/api/member/exec-status")
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .param("status", ExecStatus.W.name())
                        .with(csrf().asHeader()))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document(
                        "updateExecStatus"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , formParameters(
                                parameterWithName("status").description("헬스 상태 (W : 준비중, H : 헬스중, I : 부상으로 쉬는중"))
                        )
                );
    }

    @Test
    @TestMemberAuth
    @DisplayName("파트너 구함 상태 수정")
    void updatePartnerFindStatusToW() throws Exception{
        mockMvc.perform(
                patch("/api/member/partner-find-status")
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .param("status", PartnerFindStatus.Y.name())
                        .with(csrf().asHeader()))
                .andExpect(status().isNoContent())
                .andDo(document(
                        "updatePartnerFindStatus"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , formParameters(
                                parameterWithName("status").description("파트너 구함 상태 (Y, N)"))
                        )

                );
    }

    @Test
    @TestMemberAuth
    @DisplayName("사용자 검색 - 리스트")
    void searchMemberList() throws Exception{
        mockMvc.perform(
                get("/api/member/search")
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SEARCH_MEMBER_REQUEST_DTO)))
                .andExpect(content().string(objectMapper.writeValueAsString(SEARCH_MEMBER_SUMMARY_DTO)))
                .andDo(print())
                .andDo(document(
                        "searchMemberList"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , requestFields(
                                fieldWithPath("fromAge").description("최소 나이")
                                , fieldWithPath("toAge").description("최대 나이")
                                , fieldWithPath("sex").description("성별 (남 : M, 여 : W)")
                                , fieldWithPath("mbti").description("MBTI")
                                , fieldWithPath("execStartTime").description("헬스 시작시간 (HH:MM:SS)")
                                , fieldWithPath("execEndTime").description("헬스 종료시간 (HH:MM:SS)")
                                , fieldWithPath("execArea").description("헬스 지역")
                                , fieldWithPath("execDay").description("헬스 요일")
                                , fieldWithPath("execDay.sun").description("월요일")
                                , fieldWithPath("execDay.mon").description("화요일")
                                , fieldWithPath("execDay.tue").description("수요일")
                                , fieldWithPath("execDay.wed").description("목요일")
                                , fieldWithPath("execDay.thu").description("금요일")
                                , fieldWithPath("execDay.fri").description("토요일")
                                , fieldWithPath("execDay.sat").description("일요일")
                        ) , responseFields(
                                fieldWithPath("[].memberId").description("사용자 ID")
                                , fieldWithPath("[].nickname").description("닉네임")
                                , fieldWithPath("[].birthYear").description("출생 연도")
                                , fieldWithPath("[].sex").description("성별 (남 : M, 여 : W)")
                                , fieldWithPath("[].profileUrl").description("프로필 이미지 URL")
                                , fieldWithPath("[].bodyProfileUrl").description("바디 프로필 이미지 URL")
                                , fieldWithPath("[].execDay").description("헬스 요일")
                                , fieldWithPath("[].execDay.sun").description("일요일")
                                , fieldWithPath("[].execDay.mon").description("월요일")
                                , fieldWithPath("[].execDay.tue").description("화요일")
                                , fieldWithPath("[].execDay.wed").description("수요일")
                                , fieldWithPath("[].execDay.thu").description("목요일")
                                , fieldWithPath("[].execDay.fri").description("금요일")
                                , fieldWithPath("[].execDay.sat").description("토요일")
                                , fieldWithPath("[].execStartTime").description("헬스 시작 시간 (HH:MM:SS)")
                                , fieldWithPath("[].execEndTime").description("헬스 종료 시간 (HH:MM:SS)")
                        )
                ));

    }

    @Test
    @TestMemberAuth
    @DisplayName("사용자 검색 - 상세")
    void searchMemberDetail() throws Exception{
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/member/search-detail/{memberId}", VALID_MEMBER_ID)
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN"))
                .andExpect(content().string(objectMapper.writeValueAsString(SEARCH_MEMBER_DETAIL_DTO)))
                .andDo(print())
                .andDo(document(
                        "searchMemberDetail"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , pathParameters(
                                parameterWithName("memberId").description("사용자 ID")
                        ) , responseFields(
                                fieldWithPath("memberId").description("사용자 ID")
                                , fieldWithPath("nickname").description("닉네임")
                                , fieldWithPath("birthYear").description("출생 연도")
                                , fieldWithPath("height").description("출생 연도")
                                , fieldWithPath("weight").description("출생 연도")
                                , fieldWithPath("mbti").description("출생 연도")
                                , fieldWithPath("sex").description("성별 (남 : M, 여 : W)")
                                , fieldWithPath("execDay").description("헬스 요일")
                                , fieldWithPath("execDay.sun").description("일요일")
                                , fieldWithPath("execDay.mon").description("월요일")
                                , fieldWithPath("execDay.tue").description("화요일")
                                , fieldWithPath("execDay.wed").description("수요일")
                                , fieldWithPath("execDay.thu").description("목요일")
                                , fieldWithPath("execDay.fri").description("금요일")
                                , fieldWithPath("execDay.sat").description("토요일")
                                , fieldWithPath("execStartTime").description("헬스 시작 시간 (HH:MM:SS)")
                                , fieldWithPath("execEndTime").description("헬스 종료 시간 (HH:MM:SS)")
                                , fieldWithPath("div").description("분할 방식")
                                , fieldWithPath("execArea").description("헬스 지역")
                                , fieldWithPath("placeName").description("헬스장 이름")
                                , fieldWithPath("address").description("헬스장 주소")
                                , fieldWithPath("x").description("헬스장 x 좌표")
                                , fieldWithPath("y").description("헬스장 y 좌표")
                                , fieldWithPath("spclNote").description("특이사항")
                                , fieldWithPath("benchPress").description("3대 벤치프레스 무게")
                                , fieldWithPath("squat").description("3대 스쿼트 무게")
                                , fieldWithPath("deadlift").description("3대 데드리프트 무게")
                                , fieldWithPath("healthMotto").description("헬스 좌우명")
                        )
                ));
    }
}
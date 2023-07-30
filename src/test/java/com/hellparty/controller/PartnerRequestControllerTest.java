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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * title        : PartnerRequestController Test
 * author       : sim
 * date         : 2023-07-07
 * description  : PartnerRequestController Test
 */

@WebMvcTest(PartnerRequestController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(HttpEncodingAutoConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
class PartnerRequestControllerTest implements TestFixture {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private PartnerRequestService partnerRequestService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation){
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .addFilter(encodingFilter)
                .build();

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
    @DisplayName("유효한 사용자에게 파트너 요청")
    void requestPartnerWithValidMemberId() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.post("/api/partner-req/{toMemberId}", VALID_MEMBER_ID)
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("requestPartner"
                        ,requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , pathParameters(
                            parameterWithName("toMemberId").description("사용자 ID")
                        ))
                );
    }

    @Test
    @TestMemberAuth
    @DisplayName("유효하지 않은 사용자에게 파트너 요청")
    void requestPartnerWithInvalidMemberId() throws Exception {
        mockMvc.perform(
                post("/api/partner-req/{toMemberId}", INVALID_MEMBER_ID)
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isNotFound())
                .andExpect(content().string(containsString("대상 사용자를 찾을 수 없습니다. 다시 시도해주세요.")));
    }

    @Test
    @TestMemberAuth
    @DisplayName("파트너를 구하지 않는 사용자에게 파트너 요청")
    void requestPartnerToNotLookingForPartner() throws Exception {
        mockMvc.perform(
                        post("/api/partner-req/{toMemberId}", NOT_LOOKING_FOR_PARTNER_MEMBER_ID)
                                .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                ).andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("대상 사용자는 파트너를 구하지 않는 상태입니다. 다른 사용자를 찾아보세요.")));
    }

    @Test
    @TestMemberAuth
    @DisplayName("로그인 사용자의 파트너 요청 리스트 조회")
    void getSentPartnerRequests() throws Exception{
        mockMvc.perform(
                get("/api/partner-req")
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
        ).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(PARTNER_REQUEST_HISTORY_FROM_LOGIN_MEMBER))))
                .andExpect(content().string(containsString("pageable")))
                .andDo(
                        document("getSentPartnerRequests"
                                , requestHeaders(
                                        headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                                , responseFields(
                                        fieldWithPath("content[]").description("응답 데이터 리스트")
                                        ,fieldWithPath("content[].requestId").description("파트너 요청에 대한 시퀀스")
                                        , fieldWithPath("content[].status").description("요청 상태")
                                        , fieldWithPath("content[].memberId").description("사용자 ID")
                                        , fieldWithPath("content[].nickname").description("닉네임")
                                        , fieldWithPath("content[].profileUrl").description("프로필 이미지 URL")
                                        , fieldWithPath("pageable").description("페이징 데이터")
                                        , fieldWithPath("pageable.sort").description("정렬 관련")
                                        , fieldWithPath("pageable.sort.empty").description("빈 데이터 여부")
                                        , fieldWithPath("pageable.sort.sorted").description("정렬 여부")
                                        , fieldWithPath("pageable.sort.unsorted").description("비정렬 여부")
                                        , fieldWithPath("pageable.offset").description("오프셋")
                                        , fieldWithPath("pageable.pageSize").description("페이지 사이즈")
                                        , fieldWithPath("pageable.pageNumber").description("페이지 번호")
                                        , fieldWithPath("pageable.unpaged").description("비 페이지 여부")
                                        , fieldWithPath("pageable.paged").description("페이지 여부")
                                        , fieldWithPath("last").description("마지막 페이지 여부")
                                        , fieldWithPath("totalElements").description("총 데이터 수")
                                        , fieldWithPath("totalPages").description("총 페이지 수")
                                        , fieldWithPath("size").description("페이지 사이즈")
                                        , fieldWithPath("number").description("페이지 번호")
                                        , fieldWithPath("sort").description("정렬 관련")
                                        , fieldWithPath("sort.empty").description("빈 데이터 여부")
                                        , fieldWithPath("sort.sorted").description("정렬 여부")
                                        , fieldWithPath("sort.unsorted").description("비정렬 여부")
                                        , fieldWithPath("numberOfElements").description("총 데이터 수")
                                        , fieldWithPath("first").description("첫번째 페이지 여부")
                                        , fieldWithPath("empty").description("빈 데이터 여부"))
                        )
                );
    }

    @Test
    @TestMemberAuth
    @DisplayName("로그인 사용자에게 온 파트너 요청 리스트 조회")
    void getReceivedPartnerRequests() throws Exception{
        mockMvc.perform(
                get("/api/partner-req/to-me")
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(PARTNER_REQUEST_HISTORY_TO_LOGIN_MEMBER))))
                .andExpect(content().string(containsString("pageable")))
                .andDo(print())
                .andDo(
                        document("getReceivedPartnerRequests"
                                , requestHeaders(
                                        headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                                , responseFields(
                                        fieldWithPath("content[]").description("응답 데이터 리스트")
                                        ,fieldWithPath("content[].requestId").description("파트너 요청에 대한 시퀀스")
                                        , fieldWithPath("content[].status").description("요청 상태")
                                        , fieldWithPath("content[].memberId").description("사용자 ID")
                                        , fieldWithPath("content[].nickname").description("닉네임")
                                        , fieldWithPath("content[].profileUrl").description("프로필 이미지 URL")
                                        , fieldWithPath("pageable").description("페이징 데이터")
                                        , fieldWithPath("pageable.sort").description("정렬 관련")
                                        , fieldWithPath("pageable.sort.empty").description("빈 데이터 여부")
                                        , fieldWithPath("pageable.sort.sorted").description("정렬 여부")
                                        , fieldWithPath("pageable.sort.unsorted").description("비정렬 여부")
                                        , fieldWithPath("pageable.offset").description("오프셋")
                                        , fieldWithPath("pageable.pageSize").description("페이지 사이즈")
                                        , fieldWithPath("pageable.pageNumber").description("페이지 번호")
                                        , fieldWithPath("pageable.unpaged").description("비 페이지 여부")
                                        , fieldWithPath("pageable.paged").description("페이지 여부")
                                        , fieldWithPath("last").description("마지막 페이지 여부")
                                        , fieldWithPath("totalElements").description("총 데이터 수")
                                        , fieldWithPath("totalPages").description("총 페이지 수")
                                        , fieldWithPath("size").description("페이지 사이즈")
                                        , fieldWithPath("number").description("페이지 번호")
                                        , fieldWithPath("sort").description("정렬 관련")
                                        , fieldWithPath("sort.empty").description("빈 데이터 여부")
                                        , fieldWithPath("sort.sorted").description("정렬 여부")
                                        , fieldWithPath("sort.unsorted").description("비정렬 여부")
                                        , fieldWithPath("numberOfElements").description("총 데이터 수")
                                        , fieldWithPath("first").description("첫번째 페이지 여부")
                                        , fieldWithPath("empty").description("빈 데이터 여부"))
                )
        );
    }


    @Test
    @TestMemberAuth
    @DisplayName("받은 파트너 요청에 대한 답변처리 (승락, 거절)")
    void answerPartnerRequest() throws Exception{
        mockMvc.perform(
                post("/api/partner-req/answer")
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_PARTNER_REQUEST_ANSWER))
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document(
                        "answerPartnerRequest"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , requestFields(
                                fieldWithPath("id").description("파트너 요청에 대한 시퀀스 ID")
                                , fieldWithPath("status").description("요청 답변 (Y / N)"))
                        )
                );
    }

    @Test
    @TestMemberAuth
    @DisplayName("로그인한 사용자가 했던 파트너 요청 취소")
    void cancelPartner() throws Exception{
        mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/api/partner-req/{requestId}", PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER)
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(
                        document("cancelPartner"
                                , requestHeaders(
                                        headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                                , pathParameters(
                                        parameterWithName("requestId").description("파트너 요청에 대한 시퀀스 ID")
                                )
                        )
                );

        verify(partnerRequestService).cancelPartner(LOGIN_MEMBER_ID, PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER);
    }

    @Test
    @TestMemberAuth
    @DisplayName("다른 사용자의 파트너 요청 취소 - BadRequestException")
    void cancelPartnerWithNotMatchingLoginId() throws Exception{
        mockMvc.perform(
                delete("/api/partner-req/{requestId}", PARTNER_REQUEST_ID_TO_LOGIN_MEMBER)
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN")
                        .with(csrf())
        ).andExpect(status().isBadRequest());

        verify(partnerRequestService).cancelPartner(LOGIN_MEMBER_ID, PARTNER_REQUEST_ID_TO_LOGIN_MEMBER);
    }
}
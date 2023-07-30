package com.hellparty.controller;

import attributes.TestFixture;
import attributes.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.service.PartnerService;
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
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
@ExtendWith(RestDocumentationExtension.class)
class PartnerControllerTest implements TestFixture {

    @MockBean
    private PartnerService partnerService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation){
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8", true);

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .addFilter(encodingFilter)
                .build();

        given(partnerService.getPartnerList(LOGIN_MEMBER_ID))
                .willReturn(PARTNER_DTO_LIST_FOR_LOGIN_MEMBER);
    }
    @Test
    @TestMemberAuth
    @DisplayName("로그인 사용자의 파트너 리스트 조회")
    void getPartnerListWithValidMemberId() throws Exception {
        mockMvc.perform(
                get("/api/partner/list")
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN"))

                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(PARTNER_DTO_LIST_FOR_LOGIN_MEMBER)))
                .andDo(print())
                .andDo(document("getPartnerList"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , responseFields(
                                fieldWithPath("[].id").description("파트너 리스트 ID")
                                , fieldWithPath("[].memberId").description("사용자 ID")
                                , fieldWithPath("[].nickname").description("닉네임")
                                , fieldWithPath("[].profileUrl").description("프로필 이미지 URL")
                                , fieldWithPath("[].execStatus").description("헬스 상태")
                        ))
                );
    }

    @Test
    @TestMemberAuth
    @DisplayName("파트너 쉽 삭제")
    void deletePartnership() throws Exception{
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/api/partner/{partnerId}", VALID_MEMBER_ID)
                        .with(csrf())
                        .header("Authorization", "Bearer JWT_ACCESS_TOKEN"))

                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("deletePartnership"
                        , requestHeaders(
                                headerWithName("Authorization").description("JWT_ACCESS_TOKEN"))
                        , pathParameters(
                                parameterWithName("partnerId").description("사용자 ID"))
                        )
                );

        verify(partnerService).deletePartnership(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
    }
}
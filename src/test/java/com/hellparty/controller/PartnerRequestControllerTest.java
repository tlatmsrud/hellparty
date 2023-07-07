package com.hellparty.controller;

import auth.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.dto.PartnerRequestDTO;
import com.hellparty.service.PartnerRequestService;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class PartnerRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartnerRequestService partnerRequestService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Long VALID_FROM_MEMBER_ID = 1L;
    private static final Long VALID_TO_MEMBER_ID = 2L;
    private static final PartnerRequestDTO REQUEST = PartnerRequestDTO.builder()
            .toMemberId(VALID_TO_MEMBER_ID)
            .build();

    @BeforeEach
    void setUp(){
        willDoNothing().given(partnerRequestService)
                .requestPartner(eq(VALID_FROM_MEMBER_ID), eq(VALID_TO_MEMBER_ID));
    }
    @Test
    @TestMemberAuth
    void requestPartnerWithValidId() throws Exception {
        mockMvc.perform(
                post("/api/partner-req")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(REQUEST))
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }
}
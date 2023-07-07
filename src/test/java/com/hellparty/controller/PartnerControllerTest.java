package com.hellparty.controller;

import attributes.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.dto.PartnerDTO;
import com.hellparty.service.PartnerService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class PartnerControllerTest {

    @MockBean
    private PartnerService partnerService;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    private static final Long VALID_ID = 1L;
    private static final Long PARTNER_VALID_ID = 100L;
    private PartnerDTO.Request PARTNER_REQUEST = new PartnerDTO.Request(PARTNER_VALID_ID);
    @BeforeEach
    void setUp(){

        willDoNothing().given(partnerService).RequestPartner(eq(VALID_ID), any(PartnerDTO.Request.class));

    }
    @Test
    @TestMemberAuth
    void requestPartner() throws Exception {

        mockMvc.perform(
                post("/api/partner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PARTNER_REQUEST))
                        .with(csrf())
        ).andExpect(status().isNoContent());
    }
}
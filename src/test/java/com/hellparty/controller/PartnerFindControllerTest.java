package com.hellparty.controller;

import attributes.TestFixture;
import attributes.TestMemberAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.dto.PartnerFindDTO;
import com.hellparty.service.PartnerFindService;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-12
 * description  :
 */

@WebMvcTest(PartnerFindController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(HttpEncodingAutoConfiguration.class)
class PartnerFindControllerTest implements TestFixture {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartnerFindService partnerFindService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    void setUp(){
        given(partnerFindService.searchPartnerCandidateList(eq(LOGIN_MEMBER_ID), any(PartnerFindDTO.Search.class)))
                .willReturn(PARTNER_FIND_DTO_SUMMARY_LIST);
    }

    @Test
    @TestMemberAuth
    public void searchPartnerCandidateList() throws Exception {
        mockMvc.perform(
                get("/api/find-partner/search")
                        .content(objectMapper.writeValueAsString(PARTNER_FIND_SEARCH_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"memberId\":11")));
    }

}
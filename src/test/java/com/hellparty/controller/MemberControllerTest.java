package com.hellparty.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellparty.dto.MemberDTO;
import com.hellparty.enums.Sex;
import com.hellparty.exception.NotFoundException;
import com.hellparty.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */


@WebMvcTest(MemberControllerTest.class) // Web layer만 동작하도록
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MemberDTO.Update VALID_UPDATE_REQUEST = MemberDTO.Update.builder()
            .id(1L).age(10).height(170).weight(50.1).sex(Sex.M)
            .email("test@naver.com").nickname("nickname").profileUrl("test.url")
            .build();

    private MemberDTO.Update INVALID_UPDATE_REQUEST = MemberDTO.Update.builder()
            .id(1000L).build();

    @BeforeEach
    void setUp(){

        willDoNothing()
                .given(memberService).update(VALID_UPDATE_REQUEST);

        willThrow(new NotFoundException("사용자를 찾을 수 없습니다."))
                .given(memberService).update(INVALID_UPDATE_REQUEST);

    }

    @Test
    void updateWithValidRequest() throws Exception {
        mockMvc.perform(
                put("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_UPDATE_REQUEST))
        ).andExpect(status().isOk());
    }

    @Test
    void updateWithInvalidRequest() throws Exception {
        mockMvc.perform(
                put("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_UPDATE_REQUEST))
        ).andExpect(status().isNotFound());
    }
}
package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.dto.PartnerFindDTO;
import com.hellparty.repository.PartnerFindRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * title        : PartnerFindService 테스트
 * author       : sim
 * date         : 2023-07-13
 * description  : PartnerFindService 테스트
 */
class PartnerFindServiceTest implements TestFixture {

    private final PartnerFindRepository partnerFindRepository = mock(PartnerFindRepository.class);
    private final PartnerFindService partnerFindService = new PartnerFindService(partnerFindRepository);

    @BeforeEach
    void setUp(){
        given(partnerFindRepository.searchPartnerCandidateList(eq(LOGIN_MEMBER_ID), any(PartnerFindDTO.Search.class)))
                .willReturn(PARTNER_FIND_DTO_SUMMARY_LIST);

        given(partnerFindRepository.getPartnerCandidateDetail(LOGIN_MEMBER_ID))
                .willReturn(PARTNER_FIND_DETAIL_FOR_VALID_MEMBER_ID);
    }
    @Test
    void searchPartnerCandidateList() {
        List<PartnerFindDTO.Summary> list =
                partnerFindService.searchPartnerCandidateList(LOGIN_MEMBER_ID,PARTNER_FIND_SEARCH_REQUEST);

        assertThat(list).isEqualTo(PARTNER_FIND_DTO_SUMMARY_LIST);
    }

    @Test
    void getPartnerCandidateDetail() {
        PartnerFindDTO.Detail result = partnerFindService.getPartnerCandidateDetail(LOGIN_MEMBER_ID);
        assertThat(result).isEqualTo(PARTNER_FIND_DETAIL_FOR_VALID_MEMBER_ID);
    }
}
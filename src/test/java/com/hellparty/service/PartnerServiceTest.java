package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.dto.PartnerDTO;
import com.hellparty.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-09
 * description  : 파트너 서비스 테스트
 */
class PartnerServiceTest implements TestFixture {

    private final PartnerRepository partnerRepository = mock(PartnerRepository.class);
    private final PartnerService partnerService = new PartnerService(partnerRepository);

    @BeforeEach
    void setUp(){
        given(partnerRepository.getPartnerList(LOGIN_MEMBER_ID))
                .willReturn(PARTNER_DTO_LIST_FOR_LOGIN_MEMBER);
    }
    @Test
    void getPartnerListWithValidMemberId() {
        List<PartnerDTO> partnerList = partnerService.getPartnerList(LOGIN_MEMBER_ID);

        assertThat(partnerList.get(0).getNickname()).isEqualTo("파트너1");
        assertThat(partnerList.get(1).getNickname()).isEqualTo("파트너2");
        assertThat(partnerList.get(2).getNickname()).isEqualTo("파트너3");
    }
}
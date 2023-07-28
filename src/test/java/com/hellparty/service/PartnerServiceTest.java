package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * title        : PartnerService 테스트
 * author       : sim
 * date         : 2023-07-09
 * description  : PartnerService 테스트
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
    @DisplayName("로그인 사용자에 대한 파트너 리스트 조회")
    void getPartnerListWithValidLoginMember() {
        assertThat(partnerService.getPartnerList(LOGIN_MEMBER_ID))
                .isEqualTo(PARTNER_DTO_LIST_FOR_LOGIN_MEMBER);

    }

    @Test
    @DisplayName("로그인 사용자의 파트너 삭제")
    void deletePartnerWithLoginMember(){
        partnerService.deletePartner(LOGIN_MEMBER_ID, VALID_MEMBER_ID);

        verify(partnerRepository).deleteByMemberIdAndPartnerId(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
    }
}
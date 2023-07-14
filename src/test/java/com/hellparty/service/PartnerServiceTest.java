package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.domain.PartnerEntity;
import com.hellparty.dto.PartnerDTO;
import com.hellparty.exception.NotFoundException;
import com.hellparty.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

        given(partnerRepository.findByMemberIdAndPartnerId(LOGIN_MEMBER_ID, VALID_MEMBER_ID))
                .willReturn(VALID_PARTNER_ENTITY);
    }
    @Test
    @DisplayName("로그인 사용자에 대한 파트너 리스트 조회")
    void getPartnerListWithValidLoginMember() {
        List<PartnerDTO> partnerList = partnerService.getPartnerList(LOGIN_MEMBER_ID);

        assertThat(partnerList.get(0).getNickname()).isEqualTo("파트너1");
        assertThat(partnerList.get(1).getNickname()).isEqualTo("파트너2");
        assertThat(partnerList.get(2).getNickname()).isEqualTo("파트너3");
    }

    @Test
    @DisplayName("로그인 사용자의 파트너 삭제")
    void deletePartnerWithLoginMember(){
        partnerService.deletePartner(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
        verify(partnerRepository).findByMemberIdAndPartnerId(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
        verify(partnerRepository).delete(any(PartnerEntity.class));
    }

    @Test
    @DisplayName("로그인 사용자의 유효하지 않은 파트너 삭제")
    void deletePartnerToOtherMember(){
        assertThatThrownBy(() -> partnerService.deletePartner(LOGIN_MEMBER_ID, INVALID_MEMBER_ID))
                .isInstanceOf(NotFoundException.class);
    }
}
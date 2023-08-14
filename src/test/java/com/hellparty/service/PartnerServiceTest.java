package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.domain.PartnerEntity;
import com.hellparty.exception.NotFoundException;
import com.hellparty.repository.MemberRepository;
import com.hellparty.repository.PartnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * title        : PartnerService 테스트
 * author       : sim
 * date         : 2023-07-09
 * description  : PartnerService 테스트
 */
class PartnerServiceTest implements TestFixture {

    private final PartnerRepository partnerRepository = mock(PartnerRepository.class);
    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final PartnerService partnerService = new PartnerService(partnerRepository ,memberRepository);

    @BeforeEach
    void setUp(){
        given(partnerRepository.getPartnerList(LOGIN_MEMBER_ID))
                .willReturn(PARTNER_DTO_LIST_FOR_LOGIN_MEMBER);

        given(memberRepository.findById(LOGIN_MEMBER_ID))
                .willReturn(Optional.of(LOGIN_MEMBER_ENTITY));

        given(memberRepository.findById(VALID_MEMBER_ID))
                .willReturn(Optional.of(VALID_MEMBER_ENTITY));
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
        partnerService.deletePartnership(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
        verify(partnerRepository).deletePartnership(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
    }

    @Test
    @DisplayName("파트너 등록")
    void registrationPartner(){
        partnerService.registrationPartner(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
        verify(memberRepository).findById(LOGIN_MEMBER_ID);
        verify(memberRepository).findById(VALID_MEMBER_ID);
        verify(partnerRepository,times(2)).save(any(PartnerEntity.class));
    }

    @Test
    @DisplayName("유효하지 않은 파트너 등록")
    void registrationPartnerWithInvalidMemberId(){
        assertThatThrownBy(()->partnerService.registrationPartner(LOGIN_MEMBER_ID, INVALID_MEMBER_ID))
                .isInstanceOf(NotFoundException.class);
    }
}
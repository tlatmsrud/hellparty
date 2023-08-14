package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.domain.PartnerRequestEntity;
import com.hellparty.dto.PartnerRequestDTO;
import com.hellparty.exception.BadRequestException;
import com.hellparty.exception.NotFoundException;
import com.hellparty.repository.MemberRepository;
import com.hellparty.repository.PartnerRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * title        : PartnerRequestService 테스트
 * author       : sim
 * date         : 2023-07-07
 * description  : PartnerRequestService 테스트
 */
class PartnerRequestServiceTest implements TestFixture {
    private final PartnerRequestRepository partnerRequestRepository = mock(PartnerRequestRepository.class);
    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final PartnerService partnerService = mock(PartnerService.class);

    private final PartnerRequestService partnerRequestService = new PartnerRequestService(partnerRequestRepository, memberRepository, partnerService);

    @BeforeEach
    void setUp(){
        given(memberRepository.findById(LOGIN_MEMBER_ID))
                .willReturn(Optional.of(LOGIN_MEMBER_ENTITY));

        given(memberRepository.findById(VALID_MEMBER_ID))
                .willReturn(Optional.of(VALID_MEMBER_ENTITY));

        given(memberRepository.findById(NOT_LOOKING_FOR_PARTNER_MEMBER_ID))
                .willReturn(Optional.of(NOT_LOOKING_FOR_PARTNER_MEMBER_ENTITY));

        given(memberRepository.findById(INVALID_MEMBER_ID))
                .willThrow(new NotFoundException("대상 사용자를 찾을 수 없습니다. 다시 시도해주세요."));

        given(memberRepository.findById(NOT_LOOKING_FOR_PARTNER_MEMBER_ID))
                .willThrow(new BadRequestException("대상 사용자는 파트너를 구하지 않는 상태입니다. 다른 사용자를 찾아보세요."));

        given(partnerRequestRepository.findById(PARTNER_REQUEST_ID_TO_LOGIN_MEMBER))
                .willReturn(Optional.of(PARTNER_REQUEST_ENTITY_TO_LOGIN_MEMBER));

        given(partnerRequestRepository.findById(PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER))
                .willReturn(Optional.of(PARTNER_REQUEST_ENTITY_FROM_LOGIN_MEMBER));

        given(partnerRequestRepository.findPartnerRequestList(LOGIN_MEMBER_ID, DEFAULT_PAGEABLE))
                .willReturn(new PageImpl<>(PARTNER_REQUEST_HISTORY_FROM_LOGIN_MEMBER,DEFAULT_PAGEABLE,PARTNER_REQUEST_HISTORY_FROM_LOGIN_MEMBER.size()));

        given(partnerRequestRepository.findPartnerRequestToMeList(LOGIN_MEMBER_ID, DEFAULT_PAGEABLE))
                .willReturn(new PageImpl<>(PARTNER_REQUEST_HISTORY_TO_LOGIN_MEMBER,DEFAULT_PAGEABLE,PARTNER_REQUEST_HISTORY_TO_LOGIN_MEMBER.size()));

        willDoNothing().given(partnerRequestRepository).delete(PARTNER_REQUEST_ENTITY_FROM_LOGIN_MEMBER);
    }
    @Test
    @DisplayName("유효한 사용자에게 파트너 요청")
    void requestPartnerWithValidMemberId() {
        partnerRequestService.requestPartner(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
        verify(partnerRequestRepository).save(any(PartnerRequestEntity.class));
    }

    @Test
    @DisplayName("유효하지 않은 사용자에게 파트너 요청 - NotFoundException 발생")
    void requestPartnerWithInvalidMemberId() {
        assertThatThrownBy(() -> partnerRequestService.requestPartner(LOGIN_MEMBER_ID, INVALID_MEMBER_ID))
                .isInstanceOf(NotFoundException.class);

    }

    @Test
    @DisplayName("파트너를 찾지 않는 사용자에게 파트너 요청 - BadRequestException 발생")
    void requestPartnerWithNotLookingForPartnerMember() {
        assertThatThrownBy(()-> partnerRequestService.requestPartner(LOGIN_MEMBER_ID, NOT_LOOKING_FOR_PARTNER_MEMBER_ID))
                .isInstanceOf(BadRequestException.class);

    }

    @Test
    @DisplayName("로그인 사용자에게 온 파트너 요청 수락")
    void answerPartnerRequestWithValidRequest(){
        partnerRequestService.answerPartnerRequest(LOGIN_MEMBER_ID, VALID_PARTNER_REQUEST_ANSWER);
        verify(partnerService).registrationPartner(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
    }

    @Test
    @DisplayName("로그인 사용자에게 온 유효하지 않은 파트너 요청 수락 - NotFoundException 발생")
    void answerPartnerRequestWithInvalidRequest(){
        assertThatThrownBy(() ->
                partnerRequestService.answerPartnerRequest(LOGIN_MEMBER_ID, INVALID_PARTNER_REQUEST_ANSWER))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("다른 사용자에게 온 파트너 요청 수락 - BadRequestException 발생")
    void answerPartnerRequestByDifferentMemberId(){
        assertThatThrownBy(() ->
                partnerRequestService.answerPartnerRequest(VALID_MEMBER_ID, VALID_PARTNER_REQUEST_ANSWER))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("로그인한 사용자에게 온 파트너 요청인 경우에 대한 체크")
    void isRequestForLoginMemberWithLoginMemberId(){
        assertThat(partnerRequestService.isRequestForLoginMember(LOGIN_MEMBER_ID, PARTNER_REQUEST_ENTITY_TO_LOGIN_MEMBER))
                .isTrue();
    }

    @Test
    @DisplayName("로그인한 사용자에게 온 파트너 요청이 아닌 경우에 대한 체크")
    void isRequestForLoginMemberWithNotLoginMemberId(){
        assertThat(partnerRequestService.isRequestForLoginMember(VALID_MEMBER_ID, PARTNER_REQUEST_ENTITY_TO_LOGIN_MEMBER))
                .isFalse();
    }

    @Test
    @DisplayName("로그인 사용자가 요청한 파트너 요청 리스트 조회")
    void getPartnerRequestListWithLoginMemberId(){
        Page<PartnerRequestDTO.History> result = partnerRequestService
                .getPartnerRequestList(LOGIN_MEMBER_ID, DEFAULT_PAGEABLE);

        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent()).isEqualTo(PARTNER_REQUEST_HISTORY_FROM_LOGIN_MEMBER);
    }
    @Test
    @DisplayName("로그인 사용자에게 온 파트너 요청 리스트 조회")
    void getPartnerRequestToMeListWithLoginMemberId(){
        Page<PartnerRequestDTO.History> result = partnerRequestService
                .getPartnerRequestToMeList(LOGIN_MEMBER_ID, DEFAULT_PAGEABLE);

        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent()).isEqualTo(PARTNER_REQUEST_HISTORY_TO_LOGIN_MEMBER);
    }

    @Test
    @DisplayName("로그인한 사용자가 했던 파트너 요청을 취소하기")
    void cancelPartner(){
        partnerRequestService.cancelPartner(LOGIN_MEMBER_ID, PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER);
        verify(partnerRequestRepository).findById(PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER);
        verify(partnerRequestRepository).delete(PARTNER_REQUEST_ENTITY_FROM_LOGIN_MEMBER);
    }

    @Test
    @DisplayName("다른 사용자의 파트너 요청을 취소하기 - BadRequestException")
    void cancelPartnerWithNotMatchingLoginId(){
        assertThatThrownBy(()->partnerRequestService.cancelPartner(VALID_MEMBER_ID, PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER))
                .isInstanceOf(BadRequestException.class);

        verify(partnerRequestRepository).findById(PARTNER_REQUEST_ID_FROM_LOGIN_MEMBER);

    }
}
package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.domain.PartnerRequestEntity;
import com.hellparty.dto.PartnerRequestDTO;
import com.hellparty.exception.BadRequestException;
import com.hellparty.exception.NotFoundException;
import com.hellparty.repository.MemberRepository;
import com.hellparty.repository.PartnerRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-07
 * description  :
 */
class PartnerRequestServiceTest implements TestFixture {
    private final PartnerRequestRepository partnerRequestRepository = mock(PartnerRequestRepository.class);
    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final PartnerRequestService partnerRequestService = new PartnerRequestService(partnerRequestRepository, memberRepository);

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

        given(partnerRequestRepository.findById(VALID_PARTNER_REQUEST_ID))
                .willReturn(Optional.of(PARTNER_REQUEST_TO_LOGIN_MEMBER));

        given(partnerRequestRepository.findPartnerRequestList(LOGIN_MEMBER_ID, DEFAULT_PAGEABLE))
                .willReturn(new PageImpl<>(PARTNER_REQUEST_DTO_LIST,DEFAULT_PAGEABLE,PARTNER_REQUEST_DTO_LIST.size()));

        given(partnerRequestRepository.findPartnerRequestToMeList(LOGIN_MEMBER_ID, DEFAULT_PAGEABLE))
                .willReturn(new PageImpl<>(PARTNER_REQUEST_DTO_LIST,DEFAULT_PAGEABLE,PARTNER_REQUEST_DTO_LIST.size()));
    }
    @Test
    void requestPartnerWithValidMemberId() {
        partnerRequestService.requestPartner(LOGIN_MEMBER_ID, VALID_MEMBER_ID);
        verify(partnerRequestRepository).save(any(PartnerRequestEntity.class));
    }

    @Test
    void requestPartnerWithInvalidMemberId() {
        assertThatThrownBy(() -> partnerRequestService.requestPartner(LOGIN_MEMBER_ID, INVALID_MEMBER_ID))
                .isInstanceOf(NotFoundException.class);

    }

    @Test
    void requestPartnerWithNotLookingForPartnerMember() {
        assertThatThrownBy(()-> partnerRequestService.requestPartner(LOGIN_MEMBER_ID, NOT_LOOKING_FOR_PARTNER_MEMBER_ID))
                .isInstanceOf(BadRequestException.class);

    }

    @Test
    void answerPartnerRequestWithValidRequest(){
        partnerRequestService.answerPartnerRequest(LOGIN_MEMBER_ID, VALID_PARTNER_REQUEST_ANSWER);
    }

    @Test
    void answerPartnerRequestWithInvalidRequest(){
        assertThatThrownBy(() ->
                partnerRequestService.answerPartnerRequest(LOGIN_MEMBER_ID, INVALID_PARTNER_REQUEST_ANSWER))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void answerPartnerRequestByDifferentMemberId(){
        assertThatThrownBy(() ->
                partnerRequestService.answerPartnerRequest(VALID_MEMBER_ID, VALID_PARTNER_REQUEST_ANSWER))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void answerPartnerRequestWithInvalidRequestId(){
        partnerRequestService.answerPartnerRequest(LOGIN_MEMBER_ID, VALID_PARTNER_REQUEST_ANSWER);
    }

    @Test
    void isRequestForLoginMemberWithLoginMemberId(){
        assertThat(partnerRequestService
                .isRequestForLoginMember(LOGIN_MEMBER_ID, PARTNER_REQUEST_TO_LOGIN_MEMBER))
                .isTrue();
    }

    @Test
    void isRequestForLoginMemberWithNotLoginMemberId(){
        assertThat(partnerRequestService
                .isRequestForLoginMember(VALID_MEMBER_ID, PARTNER_REQUEST_TO_LOGIN_MEMBER))
                .isFalse();
    }

    @Test
    void getPartnerRequestListWithLoginMemberId(){
        Page<PartnerRequestDTO.History> result = partnerRequestService
                .getPartnerRequestList(LOGIN_MEMBER_ID, DEFAULT_PAGEABLE);

        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent()).isEqualTo(PARTNER_REQUEST_DTO_LIST);
    }
    @Test
    void getPartnerRequestToMeListWithLoginMemberId(){
        Page<PartnerRequestDTO.History> result = partnerRequestService
                .getPartnerRequestToMeList(LOGIN_MEMBER_ID, DEFAULT_PAGEABLE);

        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent()).isEqualTo(PARTNER_REQUEST_DTO_LIST);
    }
}
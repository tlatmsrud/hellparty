package com.hellparty.service;

import attributes.TestFixture;
import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.mapper.MemberMapper;
import com.hellparty.repository.MemberHealthRepository;
import com.hellparty.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * title        : MemberService 테스트
 * author       : sim
 * date         : 2023-07-28
 * description  : MemberService 테스트
 */
class MemberServiceTest implements TestFixture {

    private final MemberHealthRepository memberHealthRepository = mock(MemberHealthRepository.class);
    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final MemberMapper memberMapper = mock(MemberMapper.class);
    private final MemberService memberService = new MemberService(memberRepository, memberHealthRepository, memberMapper);

    @BeforeEach
    void setUp(){

        given(memberRepository.findById(LOGIN_MEMBER_ID))
                .willReturn(Optional.of(LOGIN_MEMBER_ENTITY));

        given(memberMapper.memberEntityToDto(LOGIN_MEMBER_ENTITY))
                .willReturn(LOGIN_MEMBER_DTO);

        given(memberHealthRepository.findByMemberId(LOGIN_MEMBER_ID))
                .willReturn(Optional.of(LOGIN_MEMBER_HEALTH_ENTITY));

        given(memberMapper.memberHealthEntityToDto(LOGIN_MEMBER_HEALTH_ENTITY))
                .willReturn(LOGIN_MEMBER_HEALTH_DTO);

    }

    @Test
    @DisplayName("사용자 정보 조회")
    void getDetail(){
        assertThat(memberService.getDetail(LOGIN_MEMBER_ID))
                .isEqualTo(LOGIN_MEMBER_DTO);
    }

    @Test
    @DisplayName("사용자 정보 수정")
    void updateDetail(){
        assertThatCode(() -> memberService.updateDetail(LOGIN_MEMBER_ID, UPDATE_MEMBER_DETAIL_REQUEST))
                .doesNotThrowAnyException();
        verify(memberRepository).findById(LOGIN_MEMBER_ID);
    }

    @Test
    @DisplayName("헬스정보 조회")
    void getHealthDetail(){
        assertThat(memberService.getHealthDetail(LOGIN_MEMBER_ID))
                .isEqualTo(LOGIN_MEMBER_HEALTH_DTO);
    }

    @Test
    @DisplayName("헬스 정보 수정")
    void updateHealthDetail(){
        assertThatCode(() -> memberService.updateHealthDetail(LOGIN_MEMBER_ID, UPDATE_HEALTH_DETAIL_REQUEST))
                .doesNotThrowAnyException();
        verify(memberHealthRepository).findByMemberId(LOGIN_MEMBER_ID);
    }

    @Test
    @DisplayName("운동 상태 수정")
    void updateExecStatusToW(){
        assertThatCode(() -> memberService.updateExecStatus(LOGIN_MEMBER_ID, ExecStatus.W))
                .doesNotThrowAnyException();

        verify(memberRepository).findById(LOGIN_MEMBER_ID);
    }

    @Test
    @DisplayName("파트너 찾기 상태 수정")
    void updatePartnerFindStatusToY(){
        assertThatCode(() -> memberService.updatePartnerFindStatus(LOGIN_MEMBER_ID, PartnerFindStatus.Y))
                .doesNotThrowAnyException();
        verify(memberRepository).findById(LOGIN_MEMBER_ID);

    }
}
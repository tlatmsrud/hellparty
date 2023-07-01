package com.hellparty.service;

import com.hellparty.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
class MemberServiceTest {

    private MemberService memberService;
    private MemberRepository memberRepository;

    private String VALID_EMAIL = "test@naver.com";

    private String INVALID_EMAIL = "no@naver.com";

    @BeforeEach
    void setUp(){
        memberRepository = mock(MemberRepository.class);
        memberService = new MemberService(memberRepository);

        given(memberRepository.existsMemberByEmail(VALID_EMAIL)).willReturn(true);
        given(memberRepository.existsMemberByEmail(INVALID_EMAIL)).willReturn(false);

    }
    @Test
    void isExistMemberByEmailWithValidEmail() {

        assertThat(memberService.isExistMemberByEmail(VALID_EMAIL)).isTrue();
    }

    @Test
    void isExistMemberByEmailWithInvalidEmail() {

        assertThat(memberService.isExistMemberByEmail(INVALID_EMAIL)).isFalse();
    }


}
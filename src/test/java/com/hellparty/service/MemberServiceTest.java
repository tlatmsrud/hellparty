package com.hellparty.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
class MemberServiceTest {

    private MemberService memberService = new MemberService();

    private String VALID_EMAIL = "test@naver.com";

    private String INVALID_EMAIL = "no@naver.com";

    @BeforeEach
    void setUp(){


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
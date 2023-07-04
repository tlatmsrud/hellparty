package com.hellparty.service;

import com.hellparty.domain.Member;
import com.hellparty.domain.MemberHealth;
import com.hellparty.domain.embedded.BigThree;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.MemberHealthDTO;
import com.hellparty.enums.Division;
import com.hellparty.enums.Sex;
import com.hellparty.exception.NotFoundException;
import com.hellparty.repository.MemberHealthRepository;
import com.hellparty.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
class MemberServiceTest {

    private MemberHealthRepository memberHealthRepository = mock(MemberHealthRepository.class);
    private MemberRepository memberRepository = mock(MemberRepository.class);
    private MemberService memberService = new MemberService(memberRepository, memberHealthRepository);
    private String VALID_EMAIL = "test@naver.com";
    private String INVALID_EMAIL = "no@naver.com";

    private final Long VALID_ID = 1L;
    private final Long INVALID_ID = 1000L;
    private final MemberDTO.Update UPDATE_REQUEST = MemberDTO.Update.builder()
            .nickname("update-nickname")
            .age(10)
            .sex(Sex.M)
            .build();

    @BeforeEach
    void setUp(){

        given(memberRepository.existsMemberByEmail(VALID_EMAIL))
                .willReturn(true);

        given(memberRepository.existsMemberByEmail(INVALID_EMAIL))
                .willReturn(false);

        given(memberRepository.findById(VALID_ID))
                .willReturn(Optional.of(
                        Member.builder()
                                .id(VALID_ID)
                                .email("test@naver.com")
                                .build()
                ));

        given(memberHealthRepository.findById(VALID_ID))
                .willReturn(Optional.of(
                        MemberHealth.builder()
                                .id(VALID_ID)
                                .div(Division.THREE)
                                .bigThree(BigThree.builder()
                                        .squat(100.5)
                                        .daedlift(100)
                                        .benchPress(100)
                                        .build())
                                .build()
                ));

        given(memberHealthRepository.findById(INVALID_ID))
                .willReturn(Optional.empty());

    }
    @Test
    void isExistMemberByEmailWithValidEmail() {

        assertThat(memberService.isExistMemberByEmail(VALID_EMAIL)).isTrue();
    }

    @Test
    void isExistMemberByEmailWithInvalidEmail() {

        assertThat(memberService.isExistMemberByEmail(INVALID_EMAIL)).isFalse();
    }

    @Test
    void getDetail(){
        MemberDTO memberDTO = memberService.getDetail(VALID_ID);
        assertThat(memberDTO.getId()).isEqualTo(VALID_ID);
        assertThat(memberDTO.getEmail()).isEqualTo("test@naver.com");
    }

    @Test
    void getHealthDetailWithValidId(){
        MemberHealthDTO memberHealthDTO = memberService.getHealthDetail(VALID_ID);
        assertThat(memberHealthDTO.getId()).isEqualTo(VALID_ID);
        assertThat(memberHealthDTO.getBigThree().getSquat()).isEqualTo(100.5);
    }

    @Test
    void getHealthDetailWithInvalidId(){
        assertThatThrownBy(()-> memberService.getHealthDetail(INVALID_ID))
                .isInstanceOf(NotFoundException.class);

    }
    @Test
    void updateDetail(){
        assertThatNoException().isThrownBy(() -> memberService.update(VALID_ID, UPDATE_REQUEST));
    }


}
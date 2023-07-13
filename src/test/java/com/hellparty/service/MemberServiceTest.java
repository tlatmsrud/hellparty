package com.hellparty.service;

import com.hellparty.domain.MemberEntity;
import com.hellparty.domain.MemberHealthEntity;
import com.hellparty.domain.embedded.Address;
import com.hellparty.domain.embedded.BigThree;
import com.hellparty.dto.ExecDayDTO;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.MemberHealthDTO;
import com.hellparty.enums.ExecStatus;
import com.hellparty.enums.PartnerFindStatus;
import com.hellparty.enums.Sex;
import com.hellparty.exception.BadRequestException;
import com.hellparty.exception.NotFoundException;
import com.hellparty.mapper.MemberMapper;
import com.hellparty.repository.MemberHealthRepository;
import com.hellparty.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
class MemberServiceTest {

    private final MemberHealthRepository memberHealthRepository = mock(MemberHealthRepository.class);
    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final MemberMapper memberMapper = mock(MemberMapper.class);
    private final MemberService memberService = new MemberService(memberRepository, memberHealthRepository, memberMapper);
    private final String VALID_EMAIL = "test@naver.com";
    private final String INVALID_EMAIL = "no@naver.com";
    private final Long VALID_ID = 1L;
    private final Long INVALID_ID = 1000L;
    private final MemberDTO.Update UPDATE_REQUEST = MemberDTO.Update.builder()
            .nickname("update-nickname")
            .age(10)
            .sex(Sex.M)
            .build();

    private final MemberHealthDTO.Update UPDATE_HEALTH_DETAIL_REQUEST = MemberHealthDTO.Update.builder()
            .id(VALID_ID)
            .execStartTime(Time.valueOf("19:00:00"))
            .execEndTime(Time.valueOf("20:00:00"))
            .bigThree(BigThree.builder()
                    .squat(100)
                    .deadlift(110)
                    .benchPress(80)
                    .build())
            .execArea(123L)
            .healthMotto("뇌는 근육으로 이루어져있다.")
            .gymAddress(Address.builder()
                    .y(1L)
                    .x(2L)
                    .address("서울시 중랑구")
                    .placeName("중랑헬스장")
                    .build())
            .execDay(new ExecDayDTO(false, true, true, true, true, true, false))
            .build();

    @BeforeEach
    void setUp(){

        given(memberRepository.existsMemberByEmail(VALID_EMAIL))
                .willReturn(true);

        given(memberRepository.existsMemberByEmail(INVALID_EMAIL))
                .willReturn(false);

        given(memberRepository.findById(VALID_ID))
                .willReturn(Optional.of(MemberEntity.builder().id(VALID_ID).build()));

        given(memberHealthRepository.findById(VALID_ID))
                .willReturn(Optional.of(MemberHealthEntity.builder().id(VALID_ID).build()));

        given(memberHealthRepository.findById(INVALID_ID))
                .willReturn(Optional.empty());

        given(memberMapper.memberHealthUpdateDtoToEntity(any(MemberHealthDTO.Update.class)))
                .willReturn(MemberHealthEntity.builder().id(VALID_ID).build());

        given(memberMapper.memberEntityToDto(any(MemberEntity.class)))
                .willReturn(MemberDTO.builder().id(VALID_ID).build());

        given(memberMapper.memberHealthEntityToDto(any(MemberHealthEntity.class)))
                .willReturn(MemberHealthDTO.builder().id(VALID_ID).build());
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
    }

    @Test
    void getHealthDetailWithValidId(){
        MemberHealthDTO memberHealthDTO = memberService.getHealthDetail(VALID_ID);
        assertThat(memberHealthDTO.getId()).isEqualTo(VALID_ID);
    }

    @Test
    void getHealthDetailWithInvalidId(){
        assertThatThrownBy(()-> memberService.getHealthDetail(INVALID_ID))
                .isInstanceOf(NotFoundException.class);

    }
    @Test
    void updateDetail(){
        assertThatNoException().isThrownBy(() -> memberService.updateDetail(VALID_ID, UPDATE_REQUEST));
    }

    @Test
    void updateHealthDetailWithValidId(){
        assertThatNoException().isThrownBy(() -> memberService.updateHealthDetail(VALID_ID, UPDATE_HEALTH_DETAIL_REQUEST));
        verify(memberHealthRepository).save(any(MemberHealthEntity.class));
    }

    @Test
    void updateHealthDetailWithInvalidId(){
        assertThatThrownBy(() -> memberService.updateHealthDetail(INVALID_ID, UPDATE_HEALTH_DETAIL_REQUEST))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void updateExecStatusToW(){
        memberService.updateExecStatus(VALID_ID, ExecStatus.W);
        verify(memberRepository).findById(VALID_ID);
    }

    @Test
    void updatePartnerFindStatusToY(){
        memberService.updatePartnerFindStatus(VALID_ID, PartnerFindStatus.Y);
        verify(memberRepository).findById(VALID_ID);
    }
}
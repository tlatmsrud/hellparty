package com.hellparty.service;

import attributes.TestFixture;
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
class MemberServiceTest implements TestFixture {

    private final MemberHealthRepository memberHealthRepository = mock(MemberHealthRepository.class);
    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final MemberMapper memberMapper = mock(MemberMapper.class);
    private final MemberService memberService = new MemberService(memberRepository, memberHealthRepository, memberMapper);

    private final MemberDTO.Update UPDATE_REQUEST = MemberDTO.Update.builder()
            .nickname("update-nickname")
            .age(10)
            .sex(Sex.M)
            .build();

    private final MemberHealthDTO.Update UPDATE_HEALTH_DETAIL_REQUEST = MemberHealthDTO.Update.builder()
            .id(LOGIN_MEMBER_ID)
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

        given(memberRepository.findById(LOGIN_MEMBER_ID))
                .willReturn(Optional.of(MemberEntity.builder().id(LOGIN_MEMBER_ID).build()));

        given(memberHealthRepository.findById(LOGIN_MEMBER_ID))
                .willReturn(Optional.of(MemberHealthEntity.builder().id(LOGIN_MEMBER_ID).build()));

        given(memberHealthRepository.findById(INVALID_MEMBER_ID))
                .willReturn(Optional.empty());

        given(memberMapper.memberHealthUpdateDtoToEntity(any(MemberHealthDTO.Update.class)))
                .willReturn(MemberHealthEntity.builder().id(LOGIN_MEMBER_ID).build());

        given(memberMapper.memberEntityToDto(any(MemberEntity.class)))
                .willReturn(MemberDTO.builder().id(LOGIN_MEMBER_ID).build());

        given(memberMapper.memberHealthEntityToDto(any(MemberHealthEntity.class)))
                .willReturn(MemberHealthDTO.builder().id(LOGIN_MEMBER_ID).build());
    }

    @Test
    void getDetail(){
        MemberDTO memberDTO = memberService.getDetail(LOGIN_MEMBER_ID);
        assertThat(memberDTO.getId()).isEqualTo(LOGIN_MEMBER_ID);
    }

    @Test
    void getHealthDetailWithValidId(){
        MemberHealthDTO memberHealthDTO = memberService.getHealthDetail(LOGIN_MEMBER_ID);
        assertThat(memberHealthDTO.getId()).isEqualTo(LOGIN_MEMBER_ID);
    }

    @Test
    void getHealthDetailWithInvalidId(){
        assertThatThrownBy(()-> memberService.getHealthDetail(INVALID_MEMBER_ID))
                .isInstanceOf(NotFoundException.class);

    }
    @Test
    void updateDetail(){
        assertThatNoException().isThrownBy(() -> memberService.updateDetail(LOGIN_MEMBER_ID, UPDATE_REQUEST));
    }

    @Test
    void updateHealthDetailWithValidId(){
        assertThatNoException().isThrownBy(() -> memberService.updateHealthDetail(LOGIN_MEMBER_ID, UPDATE_HEALTH_DETAIL_REQUEST));
        verify(memberHealthRepository).save(any(MemberHealthEntity.class));
    }

    @Test
    void updateHealthDetailWithInvalidId(){
        assertThatThrownBy(() -> memberService.updateHealthDetail(INVALID_MEMBER_ID, UPDATE_HEALTH_DETAIL_REQUEST))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void updateExecStatusToW(){
        memberService.updateExecStatus(LOGIN_MEMBER_ID, ExecStatus.W);
        verify(memberRepository).findById(LOGIN_MEMBER_ID);
    }

    @Test
    void updatePartnerFindStatusToY(){
        memberService.updatePartnerFindStatus(LOGIN_MEMBER_ID, PartnerFindStatus.Y);
        verify(memberRepository).findById(LOGIN_MEMBER_ID);
    }
}
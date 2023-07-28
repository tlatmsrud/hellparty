package com.hellparty.mapper;

import com.hellparty.domain.MemberEntity;
import com.hellparty.domain.MemberHealthEntity;
import com.hellparty.domain.embedded.ExecDay;
import com.hellparty.dto.ExecDayDTO;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.MemberHealthDTO;
import com.hellparty.exception.NotFoundException;
import com.hellparty.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */

@Component
@RequiredArgsConstructor
public class MemberMapper {

    private final MemberRepository memberRepository;

    public MemberDTO memberEntityToDto(MemberEntity member){
        return MemberDTO.builder()
                .id(member.getId())
                .birthYear(member.getBirthYear())
                .bodyProfileUrl(member.getBodyProfileUrl())
                .sex(member.getSex())
                .profileUrl(member.getProfileUrl())
                .mbti(member.getMbti())
                .weight(member.getWeight())
                .height(member.getHeight())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();

    }

    public MemberHealthDTO memberHealthEntityToDto(MemberHealthEntity memberHealth){

        return MemberHealthDTO.builder()
                .execStartTime(memberHealth.getExecStartTime())
                .execEndTime(memberHealth.getExecEndTime())
                .div(memberHealth.getDiv())
                .execArea(memberHealth.getExecArea())
                .gymAddress(memberHealth.getGymAddress())
                .spclNote(memberHealth.getSpclNote())
                .bigThree(memberHealth.getBigThree())
                .healthMotto(memberHealth.getHealthMotto())
                .build();
    }

    public MemberHealthEntity memberHealthUpdateDtoToEntity(Long memberId, MemberHealthDTO.Update dto){

        MemberEntity findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다. 관리자에게 문의해주세요."));

        return MemberHealthEntity.builder()
                .member(findMember)
                .execStartTime(dto.getExecStartTime())
                .execEndTime(dto.getExecEndTime())
                .div(dto.getDiv())
                .execArea(dto.getExecArea())
                .gymAddress(dto.getGymAddress())
                .healthMotto(dto.getHealthMotto())
                .spclNote(dto.getSpclNote())
                .bigThree(dto.getBigThree())
                .execDay(execDayDtoToEntity(dto.getExecDay()))
                .build();
    }

    public ExecDay execDayDtoToEntity(ExecDayDTO dto){
        return new ExecDay(dto.isSun(), dto.isMon(), dto.isTue(), dto.isWed(), dto.isThu(), dto.isFri(), dto.isSat());
    }

}

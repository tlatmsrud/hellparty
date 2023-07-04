package com.hellparty.mapper;

import com.hellparty.domain.Member;
import com.hellparty.domain.MemberHealth;
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

    public MemberDTO memberEntityToDto(Member member){
        return MemberDTO.builder()
                .id(member.getId())
                .age(member.getAge())
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

    public MemberHealthDTO memberHealthEntityToDto(MemberHealth memberHealth){

        return MemberHealthDTO.builder()
                .id(memberHealth.getId())
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

    public MemberHealth memberHealthUpdateDtoToEntity(MemberHealthDTO.Update dto){

        Member findMember = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다. 관리자에게 문의해주세요."));

        return MemberHealth.builder()
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

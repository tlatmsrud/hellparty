package com.hellparty.service;

import com.hellparty.domain.Member;
import com.hellparty.dto.MemberDTO;
import com.hellparty.exception.NotFoundException;
import com.hellparty.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean isExistMemberByEmail(String email){

        return memberRepository.existsMemberByEmail(email);
    }

    public void update(MemberDTO.Update memberDTO){

        Member findMember = memberRepository.findById(memberDTO.getId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        findMember.updateMember(
                memberDTO.getNickname()
                ,memberDTO.getEmail()
                ,memberDTO.getHeight()
                ,memberDTO.getWeight()
                ,memberDTO.getAge()
                ,memberDTO.getSex()
                ,memberDTO.getMbti()
                ,memberDTO.getProfileUrl()
        );
    }
}

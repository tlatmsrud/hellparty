package com.hellparty.mapper;

import com.hellparty.domain.Member;
import com.hellparty.dto.MemberDTO;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
public class MemberMapper {

    public static MemberDTO entityToDTO(Member member){
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
}

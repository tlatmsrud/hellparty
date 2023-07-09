package com.hellparty.mapper;

import com.hellparty.domain.PartnerRequestEntity;
import com.hellparty.dto.MemberDTO;
import com.hellparty.dto.PartnerRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-07
 * description  :
 */

@Component
@RequiredArgsConstructor
public class PartnerRequestMapper {

    private final MemberMapper memberMapper;
    public PartnerRequestDTO entityToDto(PartnerRequestEntity partnerRequest){
        MemberDTO toMember = memberMapper.memberEntityToDto(partnerRequest.getToMember());

        return PartnerRequestDTO.builder()
                .id(partnerRequest.getId())
                .member(toMember)
                .status(partnerRequest.getResponseStatus())
                .build();

    }
}

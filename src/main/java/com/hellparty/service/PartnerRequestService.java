package com.hellparty.service;

import com.hellparty.domain.Member;
import com.hellparty.domain.PartnerRequest;
import com.hellparty.exception.BadRequestException;
import com.hellparty.exception.NotFoundException;
import com.hellparty.factory.PartnerRequestFactory;
import com.hellparty.repository.MemberRepository;
import com.hellparty.repository.PartnerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * title        : 파트너 요청 서비스
 * author       : sim
 * date         : 2023-07-07
 * description  : 파트너 요청 관리 서비스 클래스
 */

@Service
@RequiredArgsConstructor
public class PartnerRequestService {

    private final PartnerRequestRepository partnerRequestRepository;

    private final MemberRepository memberRepository;

    public void requestPartner(Long fromMemberId, Long toMemberId){

        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new NotFoundException("사용자 정보를 찾을 수 없습니다. 다시 로그인해주세요."));

        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new NotFoundException("대상 사용자를 찾을 수 없습니다. 다시 시도해주세요."));

        if(!toMember.isLookingForPartner()){
            throw new BadRequestException("대상 사용자는 파트너를 구하지 않는 상태입니다. 다른 사용자를 찾아보세요.");
        }

        PartnerRequest partnerRequest = PartnerRequestFactory.createEntity(fromMember, toMember);

        partnerRequestRepository.save(partnerRequest);
    }



}

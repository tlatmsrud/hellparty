package com.hellparty.service;

import com.hellparty.domain.MemberEntity;
import com.hellparty.domain.PartnerRequestEntity;
import com.hellparty.dto.PartnerRequestDTO;
import com.hellparty.exception.BadRequestException;
import com.hellparty.exception.NotFoundException;
import com.hellparty.factory.PartnerRequestFactory;
import com.hellparty.repository.MemberRepository;
import com.hellparty.repository.PartnerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 파트너 요청하기
     * @param fromMemberId - 요청 사용자 ID
     * @param toMemberId - 수신 사용자 ID
     */
    public void requestPartner(Long fromMemberId, Long toMemberId){

        MemberEntity fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new NotFoundException("사용자 정보를 찾을 수 없습니다. 다시 로그인해주세요."));

        MemberEntity toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new NotFoundException("대상 사용자를 찾을 수 없습니다. 다시 시도해주세요."));

        if(!toMember.isLookingForPartner()){
            throw new BadRequestException("대상 사용자는 파트너를 구하지 않는 상태입니다. 다른 사용자를 찾아보세요.");
        }

        PartnerRequestEntity partnerRequest = PartnerRequestFactory.createEntity(fromMember, toMember);

        partnerRequestRepository.save(partnerRequest);
    }


    /**
     * 파트너 요청 리스트 조회
     * @param id - 사용자 ID
     * @param pageable - 페이지 속성
     * @return 페이징 처리된 파트너 요청 리스트
     */
    public Page<PartnerRequestDTO> getPartnerRequestList(Long id, Pageable pageable) {
        return partnerRequestRepository.findPartnerRequestList(id, pageable);
    }

    /**
     * 파트너 요청 응답하기
     * @param memberId - 사용자 ID
     * @param request - 파트너 요청 응답 Dto
     */
    public void answerPartnerRequest(Long memberId, PartnerRequestDTO.Answer request){
        PartnerRequestEntity partnerRequest = partnerRequestRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("요청 데이터를 찾을 수 없습니다. 관리자에게 문의해주세요."));

        if(!isRequestForLoginMember(memberId, partnerRequest)){
            throw new BadRequestException("잘못된 요청입니다. 다시 시도해주세요.");
        }

        partnerRequest.updateStatus(request.getStatus());
    }

    /**
     *
     * @param memberId - 사용자 ID
     * @param partnerRequest - 파트너 요청 엔티티
     * @return 해당 파트너 요청의 대상이 사용자일 경우 true
     */
    public boolean isRequestForLoginMember(Long memberId, PartnerRequestEntity partnerRequest){
        return memberId.equals(partnerRequest.getToMember().getId());
    }
}

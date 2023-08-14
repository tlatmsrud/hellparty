package com.hellparty.service;

import com.hellparty.domain.MemberEntity;
import com.hellparty.domain.PartnerEntity;
import com.hellparty.dto.PartnerDTO;
import com.hellparty.exception.NotFoundException;
import com.hellparty.repository.MemberRepository;
import com.hellparty.repository.PartnerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * title        : 파트너 서비스 클래스
 * author       : sim
 * date         : 2023-07-06
 * description  : 파트너 관련 서비스 클래스
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PartnerService {

    private final PartnerRepository partnerRepository;

    private final MemberRepository memberRepository;
    /**
     * 파트너 리스트 조회
     * @param loginId - 로그인 ID
     * @return 파트너 리스트
     */
    public List<PartnerDTO> getPartnerList(Long loginId){
        return partnerRepository.getPartnerList(loginId);
    }

    /**
     * 파트너 관계 삭제
     * @param loginId - 로그인 ID
     * @param partnerId - 파트너 ID
     */
    public void deletePartnership(Long loginId, Long partnerId) {
        partnerRepository.deletePartnership(loginId, partnerId);
    }

    /**
     * 파트너 등록
     * @param member1 - 사용자 ID
     * @param member2 - 사용자 ID
     */
    public void registrationPartner(Long member1, Long member2){

        MemberEntity memberEntity1 = memberRepository.findById(member1)
                .orElseThrow(() -> new NotFoundException("등록하고자 하는 파트너 정보를 찾을 수 없습니다."));

        MemberEntity memberEntity2 = memberRepository.findById(member2)
                .orElseThrow(() -> new NotFoundException("등록하고자 하는 파트너 정보를 찾을 수 없습니다."));

        PartnerEntity partnerEntity1 = new PartnerEntity(memberEntity1, memberEntity2);
        PartnerEntity partnerEntity2 = new PartnerEntity(memberEntity2, memberEntity1);

        partnerRepository.save(partnerEntity1);
        partnerRepository.save(partnerEntity2);
    }
}

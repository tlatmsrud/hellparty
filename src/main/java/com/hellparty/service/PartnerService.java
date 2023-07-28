package com.hellparty.service;

import com.hellparty.dto.PartnerDTO;
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

    public List<PartnerDTO> getPartnerList(Long memberId){
        return partnerRepository.getPartnerList(memberId);
    }

    /**
     * 파트너 삭제
     * @param loginId - 로그인 ID
     * @param partnerId - 파트너 ID
     */
    public void deletePartner(Long loginId, Long partnerId) {
        partnerRepository.deleteByMemberIdAndPartnerId(loginId, partnerId);
    }
}

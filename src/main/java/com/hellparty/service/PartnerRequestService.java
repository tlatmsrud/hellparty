package com.hellparty.service;

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

    public void requestPartner(Long fromMemberId, Long toMemberId){

    }

}

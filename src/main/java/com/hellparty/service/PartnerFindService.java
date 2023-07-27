package com.hellparty.service;

import com.hellparty.dto.PartnerFindDTO;
import com.hellparty.repository.PartnerFindRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * title        : 파트너 구하기 기능 서비스
 * author       : sim
 * date         : 2023-07-11
 * description  : 파트너 구하기 기능에 대한 서비스
 */

@RequiredArgsConstructor
@Service
@Transactional
public class PartnerFindService {

    private final PartnerFindRepository partnerFindRepository;

    public List<PartnerFindDTO.Summary> searchPartnerCandidateList(Long loginId, PartnerFindDTO.Search request){
        return partnerFindRepository.searchPartnerCandidateList(loginId, request);
    }

    public PartnerFindDTO.Detail getPartnerCandidateDetail(Long memberId) {
        return partnerFindRepository.getPartnerCandidateDetail(memberId);
    }
}

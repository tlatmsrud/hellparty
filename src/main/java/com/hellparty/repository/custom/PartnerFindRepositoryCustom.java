package com.hellparty.repository.custom;

import com.hellparty.dto.PartnerFindDTO;

import java.util.List;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-11
 * description  :
 */
public interface PartnerFindRepositoryCustom {

    List<PartnerFindDTO.Summary> searchPartnerCandidateList(Long loginId, PartnerFindDTO.Search request);

    PartnerFindDTO.Detail getPartnerCandidateDetail(Long memberId);
}

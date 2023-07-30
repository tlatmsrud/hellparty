package com.hellparty.repository.custom;

import com.hellparty.dto.PartnerDTO;

import java.util.List;

/**
 * title        : PartnerRepositoryCustom
 * author       : sim
 * date         : 2023-07-09
 * description  : PartnerRepositoryCustom
 */
public interface PartnerRepositoryCustom {

    List<PartnerDTO> getPartnerList(Long memberId);

    void deletePartnership(Long loginId, Long partnerId);
}

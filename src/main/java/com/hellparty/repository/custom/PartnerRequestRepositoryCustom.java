package com.hellparty.repository.custom;

import com.hellparty.dto.PartnerRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-07
 * description  :
 */
public interface PartnerRequestRepositoryCustom {

    Page<PartnerRequestDTO> findPartnerRequestList(Long memberId, Pageable pageable);

}

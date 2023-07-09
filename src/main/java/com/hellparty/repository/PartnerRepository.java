package com.hellparty.repository;

import com.hellparty.domain.PartnerEntity;
import com.hellparty.repository.custom.PartnerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-06
 * description  :
 */
public interface PartnerRepository extends JpaRepository<PartnerEntity, Long>, PartnerRepositoryCustom {
}

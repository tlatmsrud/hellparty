package com.hellparty.repository;

import com.hellparty.domain.PartnerEntity;
import com.hellparty.repository.custom.PartnerFindRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * title        : 파트너 구하기 리포지토리
 * author       : sim
 * date         : 2023-07-12
 * description  : 파트너 구하기 리포지토리
 */
public interface PartnerFindRepository extends JpaRepository<PartnerEntity, Long>, PartnerFindRepositoryCustom {
}

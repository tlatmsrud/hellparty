package com.hellparty.repository;

import com.hellparty.domain.PartnerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * title        : 파트너 요청 리포지토리
 * author       : sim
 * date         : 2023-07-07
 * description  : 파트너 요청 관리 리포지토리
 */
public interface PartnerRequestRepository extends JpaRepository<PartnerRequest, Long> {

}

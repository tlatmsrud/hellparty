package com.hellparty.repository;

import com.hellparty.domain.ChattingEntity;
import com.hellparty.repository.custom.ChattingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-20
 * description  :
 */
public interface ChattingRepository extends JpaRepository<ChattingEntity, Long>, ChattingRepositoryCustom {
}

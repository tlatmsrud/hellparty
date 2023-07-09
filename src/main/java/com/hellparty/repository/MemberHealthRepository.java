package com.hellparty.repository;

import com.hellparty.domain.MemberHealthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
public interface MemberHealthRepository extends JpaRepository<MemberHealthEntity, Long> {
    
}

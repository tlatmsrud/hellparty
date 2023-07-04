package com.hellparty.repository;

import com.hellparty.domain.MemberHealth;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
public interface MemberHealthRepository extends JpaRepository<MemberHealth, Long> {
    
}

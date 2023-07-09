package com.hellparty.repository;

import com.hellparty.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-01
 * description  :
 */
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    boolean existsMemberByEmail(String email);
}

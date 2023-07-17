package com.hellparty.repository.custom;

import com.hellparty.domain.MemberEntity;

/**
 * title        : MemberRepositoryCustom
 * author       : sim
 * date         : 2023-07-14
 * description  : MemberRepositoryCustom
 */
public interface MemberRepositoryCustom {

    Long findMemberIdByEmail(String email);

    MemberEntity findByEmail(String email);
}

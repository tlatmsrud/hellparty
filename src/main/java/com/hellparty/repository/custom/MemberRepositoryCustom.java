package com.hellparty.repository.custom;

/**
 * title        : MemberRepositoryCustom
 * author       : sim
 * date         : 2023-07-14
 * description  : MemberRepositoryCustom
 */
public interface MemberRepositoryCustom {

    Long findMemberIdByEmail(String email);
}

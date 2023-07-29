package com.hellparty.repository.custom;

import com.hellparty.domain.MemberEntity;
import com.hellparty.dto.SearchMemberDTO;

import java.util.List;

/**
 * title        : MemberRepositoryCustom
 * author       : sim
 * date         : 2023-07-14
 * description  : MemberRepositoryCustom
 */
public interface MemberRepositoryCustom {

    Long findMemberIdByEmail(String email);

    MemberEntity findByEmail(String email);

    List<SearchMemberDTO.Summary> searchMemberList(Long loginId, SearchMemberDTO.Request request);

    SearchMemberDTO.Detail searchMemberDetail(Long memberId);
}

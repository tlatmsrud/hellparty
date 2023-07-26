package com.hellparty.repository.custom;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-26
 * description  :
 */
public interface ChattingRoomRepositoryCustom {
    Long findIdByMemberId(Long fromMemberId, Long toMemberId);
}

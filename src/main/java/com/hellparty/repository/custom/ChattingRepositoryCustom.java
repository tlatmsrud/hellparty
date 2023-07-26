package com.hellparty.repository.custom;

import com.hellparty.dto.ChatDTO;
import com.hellparty.dto.ChattingHistoryDTO;

import java.util.List;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-26
 * description  :
 */
public interface ChattingRepositoryCustom {

    List<ChattingHistoryDTO> findByChattingRoomId(Long roomId);

    void saveAllChatting(List<ChatDTO> list);
}

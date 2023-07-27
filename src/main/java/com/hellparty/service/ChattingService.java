package com.hellparty.service;

import com.hellparty.domain.ChattingRoomEntity;
import com.hellparty.dto.ChattingHistoryDTO;
import com.hellparty.repository.ChattingRepository;
import com.hellparty.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * title        : 채팅 서비스
 * author       : sim
 * date         : 2023-07-26
 * description  : 채팅 서비스 클래스
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ChattingService {

    private final ChattingRoomRepository chattingRoomRepository;
    private final ChattingRepository chattingRepository;
    private final RedisService redisService;
    /**
     * 채팅방 ID 조회하기
     * @param loginId - 로그인 ID
     * @param memberId - 대화 상대방 ID
     * @return 조회 또는 생성된 채팅방 ID
     */
    public Long getChattingRoom(Long loginId, Long memberId){

        Long roomId = chattingRoomRepository.findIdByMemberId(loginId, memberId);

        if(roomId != null){
            return roomId;
        }

        ChattingRoomEntity chattingRoom = new ChattingRoomEntity(loginId,memberId);

        return chattingRoomRepository.save(chattingRoom).getId();
    }


    public List<ChattingHistoryDTO> getChattingHistory(Long roomId) {
        List<ChattingHistoryDTO> dbList = chattingRepository.findByChattingRoomId(roomId);
        List<ChattingHistoryDTO> cachingList = redisService.getChattingHistory(roomId.toString());

        dbList.addAll(cachingList);
        return dbList;
    }

}

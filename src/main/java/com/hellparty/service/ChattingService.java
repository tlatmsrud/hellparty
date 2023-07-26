package com.hellparty.service;

import com.hellparty.domain.ChattingRoomEntity;
import com.hellparty.dto.ChatDTO;
import com.hellparty.dto.ChattingHistoryDTO;
import com.hellparty.repository.ChattingRepository;
import com.hellparty.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    private final RedisTemplate<String, ChatDTO> redisTemplate;
    private final ChattingRepository chattingRepository;

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
        List<ChattingHistoryDTO> dbList = getChatHistoryForDB(roomId);
        List<ChattingHistoryDTO> cachingList = getChatHistoryForRedis(roomId);

        dbList.addAll(cachingList);
        return dbList;
    }

    public List<ChattingHistoryDTO> getChatHistoryForDB(Long roomId){
        return chattingRepository.findByChattingRoomId(roomId);
    }

    public List<ChattingHistoryDTO> getChatHistoryForRedis(Long roomId){
        ListOperations<String, ChatDTO> listOperations = redisTemplate.opsForList();
        List<ChatDTO> list = listOperations.range(String.valueOf(roomId), 0, -1);


        return list.stream().
                map(chatDTO -> new ChattingHistoryDTO(
                        chatDTO.getWriterId()
                        ,chatDTO.getMessage()
                        ,chatDTO.getTime()))
                .collect(Collectors.toList());
    }
}

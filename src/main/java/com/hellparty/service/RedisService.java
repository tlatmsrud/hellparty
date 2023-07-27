package com.hellparty.service;

import com.hellparty.dto.ChatDTO;
import com.hellparty.dto.ChattingHistoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * title        :
 * author       : sim
 * date         : 2023-07-27
 * description  :
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final static String CHATTING_KEY_PREFIX = "CHAT_";
    private final RedisTemplate<String, ChatDTO> redisTemplate;

    /**
     * 채팅 내역 조회
     * @param roomId - 채팅방 ID
     * @return 채팅 내역 리스트
     */
    public List<ChattingHistoryDTO> getChattingHistory(String roomId){
        ListOperations<String, ChatDTO> listOperations = redisTemplate.opsForList();
        List<ChatDTO> list = listOperations.range(String.valueOf(roomId), 0, -1);

        return list.stream().
                map(chatDTO -> new ChattingHistoryDTO(
                        chatDTO.getWriterId()
                        ,chatDTO.getMessage()
                        ,chatDTO.getTime()))
                .collect(Collectors.toList());
    }

    /**
     * 채팅 내역 저장
     * @param chatDto - 채팅 DTO
     */
    public void saveChatting(ChatDTO chatDto){
        redisTemplate.opsForList().rightPush(CHATTING_KEY_PREFIX+chatDto.getRoomId(), chatDto);
    }

    /**
     * Redis에 저장된 전체 채팅내역 조회
     * @return 채팅 내역
     */
    public List<ChatDTO> getAllChattingList(Set<String> chattingKeySet){

        ListOperations<String, ChatDTO> operations = redisTemplate.opsForList();

        List<ChatDTO> list = new ArrayList<>();

        for(String key : chattingKeySet){
            log.info("roomId : "+key);
            List<ChatDTO> chattingList = operations.range(key, 0, -1);

            if(chattingList != null){
                list.addAll(chattingList);
            }
        }
        return list;
    }

    /**
     * 채팅방 key Set 조회
     * @return Key Set
     */
    public Set<String> getKeysForChatting(){
        return redisTemplate.keys(CHATTING_KEY_PREFIX+"*");
    }

    public void delete(Set<String> keySet){
        redisTemplate.delete(keySet);
    }

}

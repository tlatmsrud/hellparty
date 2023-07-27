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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * title        : Redis 서비스
 * author       : sim
 * date         : 2023-07-27
 * description  : 레디스 관련 서비스 클래스
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final static String CHATTING_KEY_PREFIX = "CHAT_";
    private final static String REFRESH_KEY_PREFIX = "RT_";
    private final static Long REFRESH_TOKEN_TIMEOUT_DAY = 7L;
    private final RedisTemplate<String, ChatDTO> chatRedisTemplate;
    private final RedisTemplate<String, String> tokenRedisTemplate;

    /**
     * 리프레시 토큰 저장
     * @param memberId - 사용자 ID
     * @param refreshToken - 리프레시 토큰
     */
    public void saveRefreshToken(String memberId, String refreshToken){

        tokenRedisTemplate.opsForValue().set(
                REFRESH_KEY_PREFIX + memberId, refreshToken, REFRESH_TOKEN_TIMEOUT_DAY, TimeUnit.DAYS);
    }
    /**
     * 채팅 내역 조회
     * @param roomId - 채팅방 ID
     * @return 채팅 내역 리스트
     */
    public List<ChattingHistoryDTO> getChattingHistory(String roomId){
        ListOperations<String, ChatDTO> listOperations = chatRedisTemplate.opsForList();
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
        chatRedisTemplate.opsForList()
                .rightPush(CHATTING_KEY_PREFIX+chatDto.getRoomId(), chatDto);
    }

    /**
     * Redis에 저장된 전체 채팅내역 조회
     * @return 채팅 내역
     */
    public List<ChatDTO> getAllChattingList(Set<String> chattingKeySet){

        ListOperations<String, ChatDTO> operations = chatRedisTemplate.opsForList();

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
        return chatRedisTemplate.keys(CHATTING_KEY_PREFIX+"*");
    }

    /**
     * 채팅방 삭제
     * @param keySet - Key Set
     */
    public void delete(Set<String> keySet){
        chatRedisTemplate.delete(keySet);
    }

}
